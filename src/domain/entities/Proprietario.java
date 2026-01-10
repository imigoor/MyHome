package domain.entities;

import domain.enums.RoleUsuario;
import domain.enums.TipoNotificacao;

public class Proprietario extends Usuario {

    public Proprietario(String nome, String email, String cpf, String telefone, TipoNotificacao notificacao) {
        // Proprietário nasce sempre com Role ANUNCIANTE
        super(nome, email, cpf, telefone, RoleUsuario.ANUNCIANTE, notificacao);
    }


    @Override
    public boolean podeAnunciar() { return true; }
}
