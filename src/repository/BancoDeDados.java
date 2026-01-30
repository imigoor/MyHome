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
