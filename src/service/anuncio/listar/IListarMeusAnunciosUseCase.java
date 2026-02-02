package service.anuncio.listar;

import domain.anuncio.Anuncio;
import domain.entities.Usuario;

import java.util.List;

public interface IListarMeusAnunciosUseCase {
    List<Anuncio> execute(Usuario usuario);
}
