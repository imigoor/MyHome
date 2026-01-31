package repository;

import domain.anuncio.Anuncio;
import domain.entities.Corretor;
import domain.entities.Interessado;
import domain.entities.Usuario;
import domain.enums.TipoNotificacao;

import java.util.ArrayList;
import java.util.List;

public class BancoDeDados {
    private static BancoDeDados instanciaDoBanco;

    private List<Anuncio> tabelaAnuncios;
    private List<Usuario> tabelaUsuarios;

    private BancoDeDados(){
        this.tabelaAnuncios = new ArrayList<>();
        this.tabelaUsuarios = new ArrayList<>();

        Corretor corretor = new Corretor("Flavin", "flavin@gmail.com", "senha","83988770387", null, "CRECI123", null, "114.540.786-90");
    tabelaUsuarios.add(corretor);

        Interessado interessado = new Interessado("Junior interessado", "interessado@gmail.com", "senha","839123312343", null, "104.623.542-10");
    tabelaUsuarios.add(interessado);
    }

    public static BancoDeDados getInstance(){
        if(instanciaDoBanco == null)
            instanciaDoBanco = new BancoDeDados();

        return instanciaDoBanco;
    }

    public List<Anuncio> getTabelaAnuncios() {
        return tabelaAnuncios;
    }

    public List<Usuario> getTabelaUsuarios() {
        return tabelaUsuarios;
    }
}
