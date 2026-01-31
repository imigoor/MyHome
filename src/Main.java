import controller.MenuController;
import domain.entities.Usuario;
import infra.CargaDeDados;
import repository.anuncio.AnuncioRepository;
import repository.usuario.UsuarioRepository;
import service.anuncio.buscar.BuscarAnunciosUseCase;
import service.anuncio.buscar.IBuscarAnunciosUseCase;
import service.anuncio.criar.CriarAnuncioPadraoUseCase;
import service.anuncio.criar.CriarAnuncioUseCase;
import service.anuncio.criar.ICriarAnuncioPadraoUseCase;
import service.anuncio.criar.ICriarAnuncioUseCase;
import service.anuncio.listar.IListarMeusAnunciosUseCase;
import service.anuncio.listar.ListarMeusAnunciosUseCase;
import service.anuncio.moderacao.ISubmeterAnuncioUseCase;
import service.anuncio.moderacao.SubmeterAnuncioUseCase;
import service.login.IRealizarLoginUseCase;
import service.login.RealizarLoginUseCase;
import view.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        // instanciação das peças importantes
        ConsoleUI ui = new ConsoleUI();
        UsuarioRepository userRepo = new UsuarioRepository();
        AnuncioRepository anuncioRepo = new AnuncioRepository();
        IRealizarLoginUseCase loginUC = new RealizarLoginUseCase(userRepo);        

        try {
            infra.CargaDeDados loader = new CargaDeDados(userRepo, anuncioRepo);
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
            } catch (Exception e) {
                ui.mostrarErro(e.getMessage());
            }
        }

        // UseCases
        ICriarAnuncioUseCase criarManualUseCase = new CriarAnuncioUseCase(anuncioRepo);
        ICriarAnuncioPadraoUseCase criarPadraoUseCase = new CriarAnuncioPadraoUseCase(anuncioRepo);
        IListarMeusAnunciosUseCase listarMeusAnunciosUseCase = new ListarMeusAnunciosUseCase(anuncioRepo);
        IBuscarAnunciosUseCase buscarUseCase = new BuscarAnunciosUseCase(anuncioRepo);
        ISubmeterAnuncioUseCase submeterAnuncioUseCase = new SubmeterAnuncioUseCase();        

        MenuController menu = new MenuController(ui, usuarioLogado, criarManualUseCase, criarPadraoUseCase, listarMeusAnunciosUseCase, buscarUseCase, submeterAnuncioUseCase);
        menu.iniciar();
    }
}