package patterns.decorator;

import domain.anuncio.Anuncio;
import domain.interfaces.patterns.decorator.IBuscaAnuncio;

import java.util.List;
import java.util.stream.Collectors;

public class FiltroCidade extends FiltroBaseDecorator {
    private String cidadeAlvo;

    public FiltroCidade(IBuscaAnuncio buscaDecorada, String cidadeAlvo) {
        super(buscaDecorada);
        this.cidadeAlvo = cidadeAlvo;
    }

    @Override
    public List<Anuncio> executar() {
        return super.executar().stream()
                .filter(a -> a.getImovel().getEndereco().getCidade().equalsIgnoreCase(cidadeAlvo))
                .collect(Collectors.toList());
    }
}
