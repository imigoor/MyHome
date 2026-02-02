package domain.interfaces.patterns.chain;

import domain.anuncio.Anuncio;

public interface IModeracaoHandler {
    void setNext(IModeracaoHandler handler);
    void handle(Anuncio anuncio);
}
