package repository;

import java.util.ArrayList;
import java.util.List;

import domain.anuncio.Anuncio;
import domain.entities.Usuario;

public class BancoDeDados {
    private static BancoDeDados instanciaDoBanco;

    private List<Anuncio> tabelaAnuncios;
    private List<Usuario> tabelaUsuarios;

    private BancoDeDados(){
        this.tabelaAnuncios = new ArrayList<>();
        this.tabelaUsuarios = new ArrayList<>();        
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
