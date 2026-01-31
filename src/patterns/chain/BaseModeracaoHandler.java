package patterns.chain;

import domain.anuncio.Anuncio;
import domain.interfaces.patterns.chain.ModeracaoHandler;

public class BaseModeracaoHandler implements ModeracaoHandler {
    private ModeracaoHandler next;

    @Override
    public void setNext(ModeracaoHandler handler) {
        this.next = handler;
    }

    @Override
    public void handle(Anuncio anuncio) {
        if (next != null) {
            next.handle(anuncio);
        }
    }
}
