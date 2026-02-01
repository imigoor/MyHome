package patterns.Observer;

import domain.anuncio.Anuncio;
import domain.interfaces.patterns.observer.AnuncioObserver;
import domain.interfaces.patterns.strategy.NotificacaoStrategy;

public class NotificacaoAnuncioObserver implements AnuncioObserver {
    private final NotificacaoStrategy notificacaoStrategy;

    public NotificacaoAnuncioObserver(NotificacaoStrategy notificacaoStrategy) {
        this.notificacaoStrategy = notificacaoStrategy;
    }

    @Override
    public void onEstadoAlterado(
            Anuncio anuncio,
            String estadoAnterior,
            String estadoAtual
    ) {

        String titulo = "Atualização no seu anúncio";
        String mensagem = String.format(
                "O anúncio '%s' mudou de %s para %s.",
                anuncio.getTitulo(),
                estadoAnterior,
                estadoAtual
        );

        notificacaoStrategy.enviar(
                anuncio.getAnunciante(),
                titulo,
                mensagem
        );
    }
}
