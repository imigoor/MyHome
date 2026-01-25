package domain.entities;

import domain.enums.RoleUsuario;
import domain.enums.TipoNotificacao;

public class Proprietario extends Usuario {

    public Proprietario(String nome, String email, String senha, String telefone, TipoNotificacao notificacao, String documento) {
        // Proprietário nasce sempre com Role ANUNCIANTE
        super(nome, email, senha, telefone, documento, RoleUsuario.ANUNCIANTE, notificacao);
    }

    @Override
    public boolean podeAnunciar() { return true; }
}
