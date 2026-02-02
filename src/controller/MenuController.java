package controller;

import domain.anuncio.Anuncio;
import domain.entities.Usuario;
import domain.enums.TipoAnuncio;
import domain.imovel.Endereco;
import patterns.template.ProcessoVenda;
import patterns.template.VendaAvista;
import patterns.template.VendaFinanciada;
import service.anuncio.buscar.IBuscarAnunciosUseCase;
import service.anuncio.comercial.IRealizarVendaUseCase;
import service.anuncio.criar.ICriarAnuncioPadraoUseCase;
import service.anuncio.criar.ICriarAnuncioUseCase;
import service.anuncio.listar.IListarMeusAnunciosUseCase;
import service.anuncio.moderacao.ISubmeterAnuncioUseCase;
import view.ConsoleUI;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuController {
    private ConsoleUI ui;
    private Usuario usuarioLogado;

    // Dependências (Os UseCases que esse controller usa)
    private ICriarAnuncioUseCase criarAnuncioUseCase;
    private ICriarAnuncioPadraoUseCase criarAnuncioPadraoUseCase;
    private IListarMeusAnunciosUseCase listarMeusAnunciosUseCase;
    private IBuscarAnunciosUseCase buscarAnunciosUseCase;
    private ISubmeterAnuncioUseCase submeterAnuncioUseCase;
    private IRealizarVendaUseCase realizarVendaUseCase;

    public MenuController(ConsoleUI ui,
                          Usuario usuarioLogado,
                          ICriarAnuncioUseCase criarAnuncioUseCase,
                          ICriarAnuncioPadraoUseCase criarAnuncioPadraoUseCase,
                          IListarMeusAnunciosUseCase listarMeusAnunciosUseCase,
                          IBuscarAnunciosUseCase buscarAnunciosUseCase,
                          ISubmeterAnuncioUseCase submeterAnuncioUseCase,
                          IRealizarVendaUseCase realizarVendaUseCase) {
        this.ui = ui;
        this.usuarioLogado = usuarioLogado;
        this.criarAnuncioUseCase = criarAnuncioUseCase;
        this.criarAnuncioPadraoUseCase = criarAnuncioPadraoUseCase;
        this.listarMeusAnunciosUseCase = listarMeusAnunciosUseCase;
        this.buscarAnunciosUseCase = buscarAnunciosUseCase;
        this.submeterAnuncioUseCase = submeterAnuncioUseCase;
        this.realizarVendaUseCase = realizarVendaUseCase;
    }

    // loop principal
    public void iniciar() {
        boolean rodando = true;
        while (rodando) {
            ui.limparTela();
            ui.mostrarMensagem("Olá, " + usuarioLogado.getNome() + "!");
            ui.mostrarMensagem("\n============================");
            ui.mostrarMensagem("      MYHOME - MENU");
            ui.mostrarMensagem("============================");

            // opccoes disponiveis pra todos os perfies
            ui.mostrarMensagem("1 - Buscar Imóveis (RF06)");

            // esse aqui so mostra pra perfil de anunciante
            if (usuarioLogado.podeAnunciar()) {
                ui.mostrarMensagem("2 - Criar Anúncio (RF01/RF02)");
                ui.mostrarMensagem("3 - Meus Anúncios");
                ui.mostrarMensagem("4 - Publicar/Submeter Anúncio (RF03 - Chain)");
            } else {
                // SE FOR INTERESSADO (Cliente) VÊ ISSO:
                ui.mostrarMensagem("5 - Comprar Imóvel (RF08 - Template Method)");
            }

            ui.mostrarMensagem("0. Sair");
            ui.mostrarMensagem("============================");

            String opcao = ui.lerTexto("Escolha");

            try {
                switch (opcao) {
                    case "1":
                        fluxoBuscar();
                        break;
                    case "2":
                        if (usuarioLogado.podeAnunciar())
                            fluxoMenuCriacao();
                        else
                            ui.mostrarErro("Opção inválida ou sem permissão.");
                        break;
                    case "3":
                        if (usuarioLogado.podeAnunciar())
                            fluxoListarMeusAnuncios();
                        else
                            ui.mostrarErro("Opção inválida ou sem permissão.");
                        break;
                    case "4":
                        if (usuarioLogado.podeAnunciar()) fluxoSubmeterAnuncio();
                        else ui.mostrarErro("Sem permissão.");
                        break;
                    case "5": 
                        if (!usuarioLogado.podeAnunciar()) {
                            fluxoRealizarVenda();
                        } else {
                            ui.mostrarMensagem("Opção exclusiva para compradores (Interessados).");
                        }
                    case "0":
                        rodando = false;
                        break;
                    default:
                        ui.mostrarErro("Opção inválida!");
                }
            } catch (Exception e) {
                ui.mostrarErro(e.getMessage());
            }
        }
    }

    private void fluxoRealizarVenda() {
        ui.mostrarMensagem("\n=== SIMULAR COMPRA DE IMÓVEL (Template Method) ===");

        // 1. Busca todos os anúncios para listar (passando filtro vazio)
        Map<String, Object> filtroVazio = new HashMap<>();
        List<Anuncio> todosAnuncios = buscarAnunciosUseCase.execute(filtroVazio);

        // Filtra apenas os que estão ATIVOS (Prontos para venda)
        List<Anuncio> anunciosAtivos = todosAnuncios.stream()
                .filter(a -> a.getEstadoAtual().equalsIgnoreCase("Ativo"))
                .toList();

        if (anunciosAtivos.isEmpty()) {
            ui.mostrarErro("Não há imóveis Ativos disponíveis para compra no momento.");
            return;
        }

        // 2. Lista para o usuário escolher
        for (int i = 0; i < anunciosAtivos.size(); i++) {
            Anuncio a = anunciosAtivos.get(i);
            System.out.println("[" + i + "] " + a.getTitulo() + " | R$ " + a.getValor() + " | " + a.getImovel().getEndereco().getCidade());
        }

        // 3. Seleção do Imóvel
        int index = ui.lerInteiro("\nSelecione o número do imóvel para comprar");
        if (index < 0 || index >= anunciosAtivos.size()) {
            ui.mostrarErro("Opção inválida.");
            return;
        }
        Anuncio anuncioSelecionado = anunciosAtivos.get(index);

        // 4. Seleção da Forma de Pagamento (Aqui definimos qual classe filha usar)
        ui.mostrarMensagem("\n--- Escolha a Forma de Pagamento ---");
        ui.mostrarMensagem("1 - À Vista (10% de Desconto)");
        ui.mostrarMensagem("2 - Financiamento (20% de Juros)");
        
        int pagto = ui.lerInteiro("Opção");
        ProcessoVenda processoVenda = null;

        if (pagto == 1) {
            processoVenda = new VendaAvista();
        } else if (pagto == 2) {
            processoVenda = new VendaFinanciada();
        } else {
            ui.mostrarErro("Forma de pagamento inválida.");
            return;
        }

        // 5. Executa o UseCase usando o Template Method escolhido
        ui.mostrarMensagem("\nProcessando pagamento...");
        String recibo = realizarVendaUseCase.vender(anuncioSelecionado, processoVenda);
        
        // 6. Mostra o resultado final
        ui.mostrarMensagem(recibo);
        ui.lerTexto("Pressione ENTER para continuar...");
    }

    private void fluxoBuscar() {
        ui.limparTela();
        ui.mostrarMensagem("=== BUSCA AVANÇADA (RF06 - Factory) ===");

        Map<String, Object> filtros = new HashMap<>();
        boolean adicionandoFiltros = true;

        while (adicionandoFiltros) {
            ui.mostrarMensagem("\nFiltros atuais aplicados na memória.");
            ui.mostrarMensagem("1 - Adicionar Filtro de Preço Máximo");
            ui.mostrarMensagem("2 - Adicionar Filtro de Cidade");
            ui.mostrarMensagem("3 - Adicionar Filtro de Tipo de Imóvel");
            ui.mostrarMensagem("0 - EXECUTAR BUSCA AGORA");

            String op = ui.lerTexto("Opção");

            switch (op) {
                case "1":
                    Double max = ui.lerDecimal("Valor");
                    filtros.put("precoMaximo", BigDecimal.valueOf(max));
                    break;
                case "2":
                    String cidade = ui.lerTexto("Cidade");
                    filtros.put("cidade", cidade);
                    break;
                case "3":
                    String tipo = ui.lerTexto("Tipo");
                    filtros.put("tipoImovel", tipo);
                    break;
                case "0":
                    adicionandoFiltros = false;
                    break;
                default:
                    ui.mostrarErro("Inválido");
            }
        }

        ui.mostrarMensagem("\nProcessando filtros...");
        List<Anuncio> resultados = buscarAnunciosUseCase.execute(filtros);

        if (resultados.isEmpty()) {
            ui.mostrarMensagem("Nenhum imóvel encontrado com esses filtros.");
        } else {
            ui.mostrarMensagem("Encontramos " + resultados.size() + " imóveis:\n");
            for (Anuncio a : resultados) {
                ui.mostrarMensagem("- " + a.getTitulo() + " | R$ " + a.getValor() + " | " + a.getImovel().getEndereco().getCidade());
            }
        }
        ui.lerTexto("Enter para voltar...");
    }
    
    private void fluxoSubmeterAnuncio() {
        ui.mostrarMensagem("\n=== PUBLICAR ANÚNCIO (Moderação) ===");

        // 1. Pega a lista atualizada
        List<Anuncio> meusAnuncios = listarMeusAnunciosUseCase.execute(usuarioLogado);

        if (meusAnuncios.isEmpty()) {
            ui.mostrarErro("Você não tem anúncios para publicar.");
            return;
        }

        // 2. Mostra com índice para facilitar a escolha
        for (int i = 0; i < meusAnuncios.size(); i++) {
            Anuncio a = meusAnuncios.get(i);
            System.out.println("[" + i + "] " + a.getTitulo() + " | Valor: " + a.getValor() + " | Status: " + a.getEstadoAtual());
        }

        // 3. Usuário escolhe
        Integer index = ui.lerInteiro("Digite o número do anúncio que deseja publicar");

        if (index < 0 || index >= meusAnuncios.size()) {
            ui.mostrarErro("Opção inválida.");
            return;
        }

        Anuncio anuncioSelecionado = meusAnuncios.get(index);

        // 4. Validação básica de UI antes de chamar o Chain
        if (anuncioSelecionado.jaEstaPublicado()) {
            ui.mostrarErro("Este anúncio já está publicado!");
            return;
        }

        // 5. CHAMA O CHAIN OF RESPONSIBILITY
        String resultado = submeterAnuncioUseCase.execute(anuncioSelecionado);
        ui.mostrarMensagem(resultado);
        
        ui.lerTexto("Pressione ENTER para continuar...");
    }

    // sub menu para escolher entre criar anuncio manualmente ou com informacoes pre definidas
    private void fluxoMenuCriacao() {
        boolean noSubMenu = true;

        while (noSubMenu) {
            ui.mostrarMensagem("\n=== MODO DE CRIAÇÃO ===");
            ui.mostrarMensagem("1 - Criar Personalizado (Do Zero - RF01)");
            ui.mostrarMensagem("2 - Usar Modelo Padrão (Rápido - RF02)");
            ui.mostrarMensagem("0 - Voltar ao Menu Principal");

            String opcao = ui.lerTexto("Escolha");

            switch (opcao) {
                case "1":
                    fluxoCriarAnuncioManual(); // Chama o código do RF01
                    noSubMenu = false;
                    break;

                case "2":
                    fluxoCriarPadrao();
                    noSubMenu = false;
                    break;

                case "0":
                    noSubMenu = false; // Só volta, não faz nada
                    break;

                default:
                    ui.mostrarErro("Opção inválida.");
            }
        }
    }

    public void fluxoListarMeusAnuncios(){
        ui.mostrarMensagem("\n=== MEUS ANÚNCIOS ===");

        try {
            List<Anuncio> meusAnuncios = listarMeusAnunciosUseCase.execute(usuarioLogado);

            if (meusAnuncios.isEmpty()) {
                ui.mostrarMensagem("Você ainda não possui anúncios cadastrados.");
                return;
            }

            for (Anuncio a : meusAnuncios) {
                ui.mostrarMensagem("------------------------------------------------");
                ui.mostrarMensagem("ID: " + a.getId());
                ui.mostrarMensagem("Título: " + a.getTitulo());
                ui.mostrarMensagem("Valor: R$ " + a.getValor());
                ui.mostrarMensagem("Status: " + a.getEstadoAtual());
                ui.mostrarMensagem("Imóvel: " + a.getImovel().getDescricao());
                ui.mostrarMensagem("------------------------------------------------");
            }

            // estrategia pra deixar pausado pro usuario ler
            ui.lerTexto("Pressione ENTER para voltar...");

        } catch (Exception e) {
            ui.mostrarErro("Erro ao listar anúncios: " + e.getMessage());
        }
    }

    // Fluxo do RF01
    private void fluxoCriarAnuncioManual() {
        ui.mostrarMensagem("\n=== NOVO ANÚNCIO (RF01) ===");

        String titulo = ui.lerTextoObrigatorio("Título");
        Double valorD = ui.lerDecimal("Valor (R$)");

        TipoAnuncio tipoAnuncio;
        try {
            String tipoStr = ui.lerTextoObrigatorio("Tipo (VENDA/ALUGUEL)").toUpperCase();
            tipoAnuncio = TipoAnuncio.valueOf(tipoStr);
        } catch (IllegalArgumentException e) {
            ui.mostrarErro("Tipo inválido! Digite VENDA ou ALUGUEL.");
            return;
        }

        String tipoImovel = ui.lerTexto("Tipo Imóvel (CASA/APARTAMENTO/TERRENO)").toUpperCase();

        try {
            Map<String, Object> dadosImovel = ui.coletarDadosImovel(tipoImovel);

            Anuncio anuncio = criarAnuncioUseCase.execute(
                    usuarioLogado,
                    titulo,
                    BigDecimal.valueOf(valorD),
                    tipoAnuncio,
                    tipoImovel,
                    dadosImovel
            );

            ui.mostrarMensagem("Anúncio criado com sucesso. ID: " + anuncio.getId());
            ui.mostrarMensagem("Imóvel: " + anuncio.getImovel().getDescricao());
        } catch (Exception e) {
            ui.mostrarErro("Erro ao criar anúncio: " + e.getMessage());
        }
    }

    // Fluxo do RF02
    private void fluxoCriarPadrao() {
        ui.mostrarMensagem("\n=== ANÚNCIO RÁPIDO (RF02 - Prototype) ===");

        String titulo = ui.lerTextoObrigatorio("Título do Anúncio");
        Double valorD = ui.lerDecimal("Valor (R$)");
        String tipoStr = ui.lerTextoObrigatorio("Tipo (VENDA/ALUGUEL)").toUpperCase();

        BigDecimal valor = BigDecimal.valueOf(valorD);
        TipoAnuncio tipoAnuncio;
        try {
            tipoAnuncio = TipoAnuncio.valueOf(tipoStr);
        } catch (IllegalArgumentException e) {
            ui.mostrarErro("Tipo inválido. Use 'VENDA' ou 'ALUGUEL'.");
            return;
        }

        Endereco endereco = ui.lerEndereco();

        ui.mostrarMensagem("\n--- Escolha o Padrão do Imóvel ---");
        ui.mostrarMensagem("1 - Casa Padrão (3 Quartos, Quintal, 90m²)");
        ui.mostrarMensagem("2 - Apartamento Padrão (2 Quartos, 60m²)");

        String op = ui.lerTexto("Opção");
        String chaveTemplate = "";

        if (op.equals("1")) chaveTemplate = "CASA_PADRAO";
        else if (op.equals("2")) chaveTemplate = "APTO_PADRAO";
        else {
            ui.mostrarErro("Opção inválida!");
            return;
        }

        try {
            Anuncio anuncio = criarAnuncioPadraoUseCase.execute(
                    usuarioLogado,
                    titulo,
                    valor,
                    tipoAnuncio,
                    endereco,
                    chaveTemplate
            );

            ui.mostrarMensagem("Anúncio Criado com Sucesso!!");
            ui.mostrarMensagem("ID: " + anuncio.getId());
            ui.mostrarMensagem("Imóvel: " + anuncio.getImovel().getDescricao());

        } catch (Exception e) {
            ui.mostrarErro("Erro ao criar: " + e.getMessage());
        }
    }
}
