package patterns.chain;

import domain.anuncio.Anuncio;
import domain.interfaces.patterns.chain.IModeracaoHandler;

// Handler Base para a Corrente de Moderação
public class BaseModeracaoHandler implements IModeracaoHandler {
    // Próximo Handler na Corrente
    private IModeracaoHandler next;

    // Define o próximo Handler
    @Override
    public void setNext(IModeracaoHandler handler) {
        this.next = handler;
    }

    // Chama o próximo Handler, se existir
    @Override
    public void handle(Anuncio anuncio) {
        if (next != null) {
            next.handle(anuncio);
        }
    }
}
