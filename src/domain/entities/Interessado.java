package domain.entities;

import domain.enums.RoleUsuario;
import domain.enums.TipoNotificacao;

public class Interessado extends Usuario {

    public Interessado(String nome, String email, String senha, String telefone, TipoNotificacao notificacao, String documento) {
        super(nome, email, senha, telefone, documento, RoleUsuario.COMUM, notificacao);
    }

    @Override
    public boolean podeAnunciar() { return false; }
}
