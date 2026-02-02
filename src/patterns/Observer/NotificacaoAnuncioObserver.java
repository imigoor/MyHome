package patterns.Observer;

import domain.anuncio.Anuncio;
import domain.enums.TipoNotificacao;
import domain.interfaces.patterns.observer.AnuncioObserver;
import domain.interfaces.patterns.strategy.NotificacaoStrategy;

import java.util.Map;

public class NotificacaoAnuncioObserver implements AnuncioObserver {
    private final Map<TipoNotificacao, NotificacaoStrategy> estrategias;

    public NotificacaoAnuncioObserver(
            Map<TipoNotificacao, NotificacaoStrategy> estrategias
    ) {
        this.estrategias = estrategias;
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

        TipoNotificacao preferencia = anuncio.getAnunciante().getPreferenciaNotificacao();

        NotificacaoStrategy strategy = estrategias.get(preferencia);

        if (strategy == null) {
            throw new IllegalStateException(
                    "Nenhuma estratégia configurada para " + preferencia
            );
        }

        strategy.enviar(
                anuncio.getAnunciante(),
                titulo,
                mensagem
        );
    }
}
