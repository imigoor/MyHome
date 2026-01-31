package service.anuncio.buscar;

import domain.anuncio.Anuncio;

import java.util.List;
import java.util.Map;

public interface IBuscarAnunciosUseCase {
    List<Anuncio> execute(Map<String, Object> filtrosUsuario);
}
