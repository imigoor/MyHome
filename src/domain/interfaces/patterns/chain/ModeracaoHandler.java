package domain.interfaces.patterns.chain;

import domain.anuncio.Anuncio;

public interface ModeracaoHandler {
    void setNext(ModeracaoHandler handler);
    void handle(Anuncio anuncio);
}
