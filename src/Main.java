import controller.MenuController;
import domain.entities.Usuario;
import domain.enums.TipoNotificacao;
import domain.interfaces.patterns.strategy.NotificacaoStrategy;
import infra.CargaDeDados;
import patterns.Observer.NotificacaoAnuncioObserver;
import patterns.strategy.EmailNotificacaoStrategy;
import repository.anuncio.AnuncioRepository;
import repository.usuario.UsuarioRepository;
import service.anuncio.buscar.BuscarAnunciosUseCase;
import service.anuncio.buscar.IBuscarAnunciosUseCase;
import service.anuncio.comercial.IRealizarVendaUseCase;
import service.anuncio.comercial.RealizarVendaUseCase;
import service.anuncio.criar.CriarAnuncioPadraoUseCase;
import service.anuncio.criar.CriarAnuncioUseCase;
import service.anuncio.criar.ICriarAnuncioPadraoUseCase;
import service.anuncio.criar.ICriarAnuncioUseCase;
import service.anuncio.listar.IListarMeusAnunciosUseCase;
import service.anuncio.listar.ListarMeusAnunciosUseCase;
import service.anuncio.moderacao.ISubmeterAnuncioUseCase;
import service.anuncio.moderacao.SubmeterAnuncioUseCase;
import service.anuncio.notificar.NotificacaoLogService;
import service.login.IRealizarLoginUseCase;
import service.login.RealizarLoginUseCase;
import view.ConsoleUI;

import java.util.EnumMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // instanciação das peças importantes
        ConsoleUI ui = new ConsoleUI();
        UsuarioRepository userRepo = new UsuarioRepository();
        AnuncioRepository anuncioRepo = new AnuncioRepository();
        IRealizarLoginUseCase loginUC = new RealizarLoginUseCase(userRepo);

        // Observer
        NotificacaoLogService logService = new NotificacaoLogService("logs/notificacoes.txt");
        Map<TipoNotificacao, NotificacaoStrategy> estrategias = new EnumMap<>(TipoNotificacao.class);

        // Aqui iremos colocar todas as estratégias de notificação futuras
        estrategias.put(
                TipoNotificacao.EMAIL,
                new EmailNotificacaoStrategy(logService)
        );

        NotificacaoAnuncioObserver notificacaoObserver = new NotificacaoAnuncioObserver(estrategias);

        try {
            CargaDeDados loader = new CargaDeDados(userRepo, anuncioRepo, notificacaoObserver);
            loader.carregarTudo();
            ui.mostrarMensagem("Dados carregados do CSV com sucesso!");
        } catch (Exception e) {
            ui.mostrarErro("Falha no CSV: " + e.getMessage());
        }

        Usuario usuarioLogado = null;

        // fluxo de login
        while (usuarioLogado == null) {
            ui.mostrarMensagem("=== BEM-VINDO AO MYHOME ===");
            String email = ui.lerTextoObrigatorio("Login (Email)");
            String senha = ui.lerTextoObrigatorio("Senha");

            try {
                usuarioLogado = loginUC.execute(email, senha);

                MenuController menu = getMenuController(anuncioRepo, ui, usuarioLogado, notificacaoObserver);

                menu.iniciar();
                ui.limparTela();

                usuarioLogado = null;
            } catch (Exception e) {
                ui.mostrarErro(e.getMessage());
            }
        }
    }

    private static MenuController getMenuController(AnuncioRepository anuncioRepo, ConsoleUI ui, Usuario usuarioLogado, NotificacaoAnuncioObserver notificacaoObserver) {
        // UseCases
        ICriarAnuncioUseCase criarManualUseCase = new CriarAnuncioUseCase(anuncioRepo, notificacaoObserver);
        ICriarAnuncioPadraoUseCase criarPadraoUseCase = new CriarAnuncioPadraoUseCase(anuncioRepo, notificacaoObserver);
        IListarMeusAnunciosUseCase listarMeusAnunciosUseCase = new ListarMeusAnunciosUseCase(anuncioRepo);
        IBuscarAnunciosUseCase buscarUseCase = new BuscarAnunciosUseCase(anuncioRepo);
        ISubmeterAnuncioUseCase submeterAnuncioUseCase = new SubmeterAnuncioUseCase();
        IRealizarVendaUseCase realizarVendaUseCase = new RealizarVendaUseCase();

        return new MenuController(ui, usuarioLogado, criarManualUseCase, criarPadraoUseCase, listarMeusAnunciosUseCase, buscarUseCase, submeterAnuncioUseCase, realizarVendaUseCase);
    }
}