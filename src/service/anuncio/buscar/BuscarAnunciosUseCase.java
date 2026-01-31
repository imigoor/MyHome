package service.anuncio.buscar;

import domain.anuncio.Anuncio;
import domain.interfaces.patterns.decorator.IBuscaAnuncio;
import patterns.decorator.BuscaTodosAnuncios;
import repository.anuncio.AnuncioRepository;

import java.util.List;
import java.util.Map;

public class BuscarAnunciosUseCase implements IBuscarAnunciosUseCase {

    private final AnuncioRepository repository;

    public BuscarAnunciosUseCase(AnuncioRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Anuncio> execute(Map<String, Object> filtrosUsuario) {
        IBuscaAnuncio buscaAtual = new BuscaTodosAnuncios(repository);

        for (Map.Entry<String, Object> entrada : filtrosUsuario.entrySet()) {
            String chave = entrada.getKey();
            Object valor = entrada.getValue();

            // pede pra Fábrica resolver, delega responsabilidade de instaciar o filtro pro simple factory
            buscaAtual = FiltroFactory.criar(chave, buscaAtual, valor);
        }

        return buscaAtual.executar();
    }
}