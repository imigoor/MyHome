package controller;

import domain.enums.TipoAnuncio;
import domain.imovel.Endereco;
import service.anuncio.criar.ICriarAnuncioPadraoUseCase;
import service.anuncio.criar.ICriarAnuncioUseCase;
import domain.anuncio.Anuncio;
import domain.entities.Usuario;
import view.ConsoleUI;

import java.math.BigDecimal;
import java.util.Map;

public class MenuController {
    private ConsoleUI ui;
    private Usuario usuarioLogado;

    // Dependências (Os UseCases que esse controller usa)
    private ICriarAnuncioUseCase criarAnuncioUseCase;
    private ICriarAnuncioPadraoUseCase criarAnuncioPadraoUseCase;

    public MenuController(ConsoleUI ui, Usuario usuarioLogado, ICriarAnuncioUseCase criarAnuncioUseCase, ICriarAnuncioPadraoUseCase criarAnuncioPadraoUseCase) {
        this.ui = ui;
        this.usuarioLogado = usuarioLogado;
        this.criarAnuncioUseCase = criarAnuncioUseCase;
        this.criarAnuncioPadraoUseCase = criarAnuncioPadraoUseCase;
    }

    // loop principal (estava no Main, agora está aqui, organizado)
    public void iniciar() {
        boolean rodando = true;
        while (rodando) {
            ui.mostrarMensagem("\n============================");
            ui.mostrarMensagem("      MYHOME - MENU");
            ui.mostrarMensagem("============================");

            // opccoes disponiveis pra todos os perfies
            ui.mostrarMensagem("1 - Buscar Imóveis (RF06)");

            // esse aqui so mostra pra perfil de anunciante
            if (usuarioLogado.podeAnunciar()) {
                ui.mostrarMensagem("2 - Criar Anúncio (RF01/RF02)");
                ui.mostrarMensagem("3 - Meus Anúncios");
            }

            ui.mostrarMensagem("0. Sair");
            ui.mostrarMensagem("============================");

            String opcao = ui.lerTexto("Escolha");

            try {
                switch (opcao) {
                    case "1":
                        // fluxoBuscar();
                        break;
                    case "2":
                        // se o caba for malandro e digitar "2" mesmo sem ver o menu a gente dale o bloqueio
                        if (usuarioLogado.podeAnunciar())
                            fluxoMenuCriacao();
                        else
                            ui.mostrarErro("Opção inválida ou sem permissão.");
                        break;
                    case "3":
                        if (usuarioLogado.podeAnunciar())
                            ui.mostrarMensagem("vai ser implementado ainda o '3' papai");
                            //fluxoListarMeusAnuncios();
                        else
                            ui.mostrarErro("Opção inválida ou sem permissão.");

                        break;
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

        ui.mostrarMensagem("--- Localização ---");
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
