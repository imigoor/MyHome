package patterns.decorator;

import domain.anuncio.Anuncio;
import domain.interfaces.patterns.decorator.IBuscaAnuncio;

import java.util.List;

public abstract class FiltroBaseDecorator implements IBuscaAnuncio {
    protected IBuscaAnuncio buscaDecorada;

    public FiltroBaseDecorator(IBuscaAnuncio buscaDecorada) {
        this.buscaDecorada = buscaDecorada;
    }

    @Override
    public List<Anuncio> executar() {
        return buscaDecorada.executar();
    }
}
