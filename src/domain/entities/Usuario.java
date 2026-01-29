package domain.entities;

import domain.enums.RoleUsuario;
import domain.enums.TipoNotificacao;

import java.util.UUID;

public abstract class Usuario {
    private UUID id;
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private String documento; // CPF ou CNPJ

    private RoleUsuario role;
    private TipoNotificacao preferenciaNotificacao;

    public Usuario(String nome, String email, String senha, String telefone, String documento, RoleUsuario role, TipoNotificacao preferenciaNotificacao) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.documento = documento;
        this.role = role;
        this.preferenciaNotificacao = preferenciaNotificacao;
    }

    public abstract boolean podeAnunciar();

    public UUID getId() { return id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }

    public RoleUsuario getRole() { return role; }

    public TipoNotificacao getPreferenciaNotificacao() { return preferenciaNotificacao; }
    public void setPreferenciaNotificacao(TipoNotificacao preferenciaNotificacao) { this.preferenciaNotificacao = preferenciaNotificacao; }
}