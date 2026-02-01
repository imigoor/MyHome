package domain.interfaces.patterns.observer;

import domain.anuncio.Anuncio;

public interface AnuncioObserver {
    void onEstadoAlterado(
            Anuncio anuncio,
            String estadoAnterior,
            String estadoAtual
    );
}
