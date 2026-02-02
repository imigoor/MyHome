package repository.anuncio;

import domain.anuncio.Anuncio;
import repository.BancoDeDados;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AnuncioRepository {
    private BancoDeDados db = BancoDeDados.getInstance();

    public void salvar(Anuncio anuncio) {
        db.getTabelaAnuncios().add(anuncio);
    }

    public void deletar(Anuncio anuncio){
        db.getTabelaAnuncios().remove(anuncio);
    }

    public List<Anuncio> buscarPorAnunciante(UUID idAnunciante) {
        return db.getTabelaAnuncios().stream()
                .filter(a -> a.getAnunciante().getId().equals(idAnunciante))
                .collect(Collectors.toList());
    }

    public List<Anuncio> listarTodos() {
        return db.getTabelaAnuncios().stream()
                .filter(a -> "ATIVO".equalsIgnoreCase(a.getEstadoAtual()))
                .collect(Collectors.toList());
    }
}
