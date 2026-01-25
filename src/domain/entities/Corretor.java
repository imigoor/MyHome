package domain.entities;

import domain.enums.RoleUsuario;
import domain.enums.TipoNotificacao;

public class Corretor extends Usuario {

    private String creci;
    private Double percentualComissao;

    public Corretor(String nome, String email, String senha, String telefone, TipoNotificacao notificacao, String creci, Double comissao, String documento) {
        super(nome, email, senha, telefone, documento, RoleUsuario.ANUNCIANTE, notificacao);

        this.creci = creci;
        this.percentualComissao = comissao;
    }

    public String getCreci() { return creci; }
    public void setCreci(String creci) { this.creci = creci; }

    public Double getPercentualComissao() { return percentualComissao; }
    public void setPercentualComissao(Double percentualComissao) { this.percentualComissao = percentualComissao; }

    @Override
    public boolean podeAnunciar() { return true; }
}