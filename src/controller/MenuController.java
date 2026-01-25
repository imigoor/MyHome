package controller;

import domain.enums.StatusAnuncio;
import domain.enums.TipoAnuncio;
import domain.imovel.Imovel;
import service.anuncio.criar.ICriarAnuncioUseCase;
import domain.anuncio.Anuncio;
import domain.entities.Usuario;
import view.ConsoleUI;

import java.math.BigDecimal;
import java.util.Map;

public class MenuController {
    private ConsoleUI ui;
    private Usuario usuarioLogado; // Sessão atual

    // Dependências (Os UseCases que esse controller usa)
    private ICriarAnuncioUseCase criarAnuncioUC;

    public MenuController(ConsoleUI ui, Usuario usuarioLogado, ICriarAnuncioUseCase criarAnuncioUC) {
        this.ui = ui;
        this.usuarioLogado = usuarioLogado;
        this.criarAnuncioUC = criarAnuncioUC;
    }

    // loop principal (estava no Main, agora está aqui, organizado)
    public void iniciar() {
        boolean rodando = true;
        while (rodando) {
            ui.mostrarMensagem("\n============================");
            ui.mostrarMensagem("      MYHOME - MENU");
            ui.mostrarMensagem("============================");

            // opccoes disponiveis pra todos os perfies
            ui.mostrarMensagem("1. Buscar Imóveis (RF06)");

            // esse aqui so mostra pra perfil de anunciante
            if (usuarioLogado.podeAnunciar()) {
                ui.mostrarMensagem("2. Criar Anúncio (RF01/RF02)");
                ui.mostrarMensagem("3. Meus Anúncios");
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
            ui.mostrarMensagem("1. Criar Personalizado (Do Zero - RF01)");
            ui.mostrarMensagem("2. Usar Modelo Padrão (Rápido - RF02)");
            ui.mostrarMensagem("0. Voltar ao Menu Principal");

            String opcao = ui.lerTexto("Escolha");

            switch (opcao) {
                case "1":
                    fluxoCriarAnuncioManual(); // Chama o código do RF01
                    noSubMenu = false;
                    break;

                case "2":
                    //fluxoCriarPadrao(); // Chama o código do RF02
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

    // Fluxo específico do RF01
    private void fluxoCriarAnuncioManual() {
        ui.mostrarMensagem("\n=== NOVO ANÚNCIO (RF01) ===");

        // 1. View coleta dados
        String titulo = ui.lerTextoObrigatorio("Título");
        Double valorD = ui.lerDecimal("Valor (R$)");
        String tipoAnuncio = ui.lerTextoObrigatorio("Tipo (VENDA/ALUGUEL)").toUpperCase();
        String tipoImovel = ui.lerTexto("Tipo Imóvel (CASA/APARTAMENTO/TERRENO)").toUpperCase();

        Map<String, Object> dadosImovel = ui.coletarDadosImovel(tipoImovel);

        // 2. UseCase executa a lógica

        // VERIFICAR SE FAz MAIS SENTIDO PASSAR UM DTO algo assim
        Anuncio anuncio = criarAnuncioUC.execute(
                usuarioLogado,
                titulo,
                BigDecimal.valueOf(valorD),
                tipoAnuncio,
                tipoImovel,
                dadosImovel
        );

        // 3. View mostra sucesso
        ui.mostrarMensagem("Anúncio criado com sucesso. ID: " + anuncio.getId());
    }
}
