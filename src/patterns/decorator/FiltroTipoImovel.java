package patterns.decorator;

import domain.anuncio.Anuncio;
import domain.interfaces.patterns.decorator.IBuscaAnuncio;

import java.util.List;
import java.util.stream.Collectors;

public class FiltroTipoImovel extends FiltroBaseDecorator {
    private String tipoAlvo;

    public FiltroTipoImovel(IBuscaAnuncio buscaDecorada, String tipoAlvo) {
        super(buscaDecorada);
        this.tipoAlvo = tipoAlvo;
    }

    @Override
    public List<Anuncio> executar() {
        List<Anuncio> resultados = super.executar();

        return resultados.stream()
                .filter(a -> a.getImovel().getTipoImovel().equalsIgnoreCase(tipoAlvo))
                .collect(Collectors.toList());
    }
}
