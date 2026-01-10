package domain.entities;

import domain.enums.RoleUsuario;
import domain.enums.TipoNotificacao;

public class Interessado extends Usuario {

    public Interessado(String nome, String email, String cpf, String telefone, TipoNotificacao notificacao) {
        super(nome, email, cpf, telefone, RoleUsuario.COMUM, notificacao);
    }

    @Override
    public boolean podeAnunciar() { return false; }
}
