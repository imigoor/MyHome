package domain.interfaces.patterns.decorator;

import domain.anuncio.Anuncio;

import java.util.List;

public interface IBuscaAnuncio {
    List<Anuncio> executar();
}
