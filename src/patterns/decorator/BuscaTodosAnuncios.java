package patterns.decorator;

import domain.anuncio.Anuncio;
import domain.interfaces.patterns.decorator.IBuscaAnuncio;
import repository.anuncio.AnuncioRepository;
import java.util.List;

public class BuscaTodosAnuncios implements IBuscaAnuncio {
    private AnuncioRepository repository;

    public BuscaTodosAnuncios(AnuncioRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Anuncio> executar() {
        return repository.listarTodos();
    }
}
