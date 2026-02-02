package service.anuncio.listar;

import domain.anuncio.Anuncio;
import domain.entities.Usuario;
import repository.anuncio.AnuncioRepository;

import java.util.List;

public class ListarMeusAnunciosUseCase implements IListarMeusAnunciosUseCase {
    private AnuncioRepository repository;

    public ListarMeusAnunciosUseCase(AnuncioRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Anuncio> execute(Usuario usuario) {
        return repository.buscarPorAnunciante(usuario.getId());
    }
}
