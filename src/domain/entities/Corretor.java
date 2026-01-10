package domain.entities;

import domain.enums.RoleUsuario;
import domain.enums.TipoNotificacao;

public class Corretor extends Usuario {

    private String creci;
    private Double percentualComissao;

    public Corretor(String nome, String email, String cpf, String telefone, TipoNotificacao notificacao, String creci, Double comissao) {
        super(nome, email, cpf, telefone, RoleUsuario.ANUNCIANTE, notificacao);
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