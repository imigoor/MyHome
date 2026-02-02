package domain.interfaces.patterns.strategy;

import domain.entities.Usuario;
import service.anuncio.notificar.NotificacaoLogService;

public abstract class NotificacaoStrategy {
    protected NotificacaoLogService logService;

    public NotificacaoStrategy(NotificacaoLogService logService) {
        this.logService = logService;
    }

    public void setEstrategia(NotificacaoLogService logService) {
        this.logService = logService;
    }

    public abstract void enviar(Usuario destinatario, String titulo, String mensagem);
}
