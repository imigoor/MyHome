package domain.entities;

import domain.enums.RoleUsuario;
import domain.enums.TipoNotificacao;

public abstract class Usuario {
    private Long id; // gerar no banco
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private String documento; // CPF ou CNPJ

    private RoleUsuario role;
    private TipoNotificacao preferenciaNotificacao;

    public Usuario(String nome, String email, String senha, String telefone, String documento, RoleUsuario role, TipoNotificacao preferenciaNotificacao) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.documento = documento;
        this.role = role;
        this.preferenciaNotificacao = preferenciaNotificacao;
    }

    public abstract boolean podeAnunciar();

    public Long getId() { return id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public RoleUsuario getRole() { return role; }

    public TipoNotificacao getPreferenciaNotificacao() { return preferenciaNotificacao; }
    public void setPreferenciaNotificacao(TipoNotificacao preferenciaNotificacao) { this.preferenciaNotificacao = preferenciaNotificacao; }
}