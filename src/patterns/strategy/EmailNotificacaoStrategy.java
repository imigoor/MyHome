package patterns.strategy;

import domain.entities.Usuario;
import domain.interfaces.patterns.strategy.NotificacaoStrategy;
import service.anuncio.notificar.NotificacaoLogService;

public class EmailNotificacaoStrategy extends NotificacaoStrategy {
    public EmailNotificacaoStrategy(NotificacaoLogService logService) {
        super(logService);
    }

    @Override
    public void enviar(Usuario destinatario, String titulo, String mensagem) {
        this.logService.registrarEnvio(
                "EMAIL",
                destinatario,
                titulo,
                mensagem
        );
    }
}
