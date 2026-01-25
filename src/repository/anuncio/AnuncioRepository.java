package repository.anuncio;

import domain.anuncio.Anuncio;
import repository.BancoDeDados;

import java.util.List;

public class AnuncioRepository {
    private BancoDeDados db = BancoDeDados.getInstance();

    public void salvar(Anuncio anuncio) {
        db.getTabelaAnuncios().add(anuncio);
    }

    public void deletar(Anuncio anuncio){
        db.getTabelaAnuncios().remove(anuncio);
    }

    public List<Anuncio> listarTodos() {
        return db.getTabelaAnuncios();
    }
}
