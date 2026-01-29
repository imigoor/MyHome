import controller.MenuController;
import domain.entities.Usuario;
import repository.anuncio.AnuncioRepository;
import repository.usuario.UsuarioRepository;
import service.anuncio.criar.CriarAnuncioPadraoUseCase;
import service.anuncio.criar.CriarAnuncioUseCase;
import service.anuncio.criar.ICriarAnuncioPadraoUseCase;
import service.anuncio.criar.ICriarAnuncioUseCase;
import service.login.IRealizarLoginUseCase;
import service.login.RealizarLoginUseCase;
import view.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        // instanciação das peças importantes
        ConsoleUI ui = new ConsoleUI();
        UsuarioRepository userRepo = new UsuarioRepository();
        IRealizarLoginUseCase loginUC = new RealizarLoginUseCase(userRepo);

        Usuario usuarioLogado = null;

        // fluxo de login
        while (usuarioLogado == null) {
            ui.mostrarMensagem("=== BEM-VINDO AO MYHOME ===");
            String email = ui.lerTextoObrigatorio("Login (Email)");
            String senha = ui.lerTextoObrigatorio("Senha");

            try {
                usuarioLogado = loginUC.execute(email, senha);
                ui.mostrarMensagem("Olá, " + usuarioLogado.getNome() + "! Login realizado.");
            } catch (Exception e) {
                ui.mostrarErro(e.getMessage());
            }
        }

        AnuncioRepository anuncioRepo = new AnuncioRepository();

        // UseCases
        ICriarAnuncioUseCase criarManualUseCase = new CriarAnuncioUseCase(anuncioRepo);
        ICriarAnuncioPadraoUseCase criarPadraoUseCase = new CriarAnuncioPadraoUseCase(anuncioRepo);

        MenuController menu = new MenuController(ui, usuarioLogado, criarManualUseCase, criarPadraoUseCase);
        menu.iniciar();
    }
}