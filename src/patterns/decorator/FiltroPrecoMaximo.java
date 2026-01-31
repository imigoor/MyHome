package patterns.decorator;

import domain.anuncio.Anuncio;
import domain.interfaces.patterns.decorator.IBuscaAnuncio;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class FiltroPrecoMaximo extends FiltroBaseDecorator {
    private BigDecimal valorMaximo;

    public FiltroPrecoMaximo(IBuscaAnuncio buscaDecorada, BigDecimal valorMaximo) {
        super(buscaDecorada);
        this.valorMaximo = valorMaximo;
    }

    @Override
    public List<Anuncio> executar() {
        return super.executar().stream()
                .filter(a -> a.getValor().compareTo(valorMaximo) <= 0)
                .collect(Collectors.toList());
    }
}
