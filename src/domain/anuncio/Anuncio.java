package domain.anuncio;

import domain.entities.Usuario;
import domain.enums.StatusAnuncio;
import domain.enums.TipoTransacao;
import domain.imovel.Imovel;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Anuncio {
    private Long id;
    private String titulo;
    private BigDecimal valor;
    private LocalDateTime dataCriacao;

    private TipoTransacao tipoTransacao;
    private StatusAnuncio status;

    private Imovel imovel;
    private Usuario anunciante;

    public Anuncio() {
        this.status = StatusAnuncio.RASCUNHO;
        this.dataCriacao = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public TipoTransacao getTipoTransacao() { return tipoTransacao; }
    public void setTipoTransacao(TipoTransacao tipoTransacao) { this.tipoTransacao = tipoTransacao; }

    public StatusAnuncio getStatus() { return status; }
    public void setStatus(StatusAnuncio status) { this.status = status; }

    public Imovel getImovel() { return imovel; }
    public void setImovel(Imovel imovel) { this.imovel = imovel; }

    public Usuario getAnunciante() { return anunciante; }
    public void setAnunciante(Usuario anunciante) { this.anunciante = anunciante; }
}
