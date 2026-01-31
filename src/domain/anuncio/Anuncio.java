package domain.anuncio;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import domain.entities.Usuario;
import domain.enums.StatusAnuncio;
import domain.enums.TipoAnuncio;
import domain.imovel.Imovel;

public class Anuncio {
    private UUID id;
    private String titulo;
    private BigDecimal valor;
    private LocalDateTime dataCriacao;

    private TipoAnuncio tipoAnuncio;
    private StatusAnuncio status;

    private Imovel imovel;
    private Usuario anunciante;

    public Anuncio(String titulo, BigDecimal valor, TipoAnuncio tipoAnuncio, Imovel imovel, Usuario usuario){
        this.id = UUID.randomUUID();
        this.titulo = titulo;
        this.valor = valor;
        this.tipoAnuncio = tipoAnuncio;
        this.imovel = imovel;
        this.anunciante = usuario;

        this.status = StatusAnuncio.RASCUNHO;
        this.dataCriacao = LocalDateTime.now();
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public TipoAnuncio getTipoAnuncio() { return tipoAnuncio; }
    public void setTipoAnuncio(TipoAnuncio tipoAnuncio) { this.tipoAnuncio = tipoAnuncio; }

    public StatusAnuncio getStatus() { return status; }
    public void setStatus(StatusAnuncio status) { this.status = status; }

    public Imovel getImovel() { return imovel; }
    public void setImovel(Imovel imovel) { this.imovel = imovel; }

    public Usuario getAnunciante() { return anunciante; }
    public void setAnunciante(Usuario anunciante) { this.anunciante = anunciante; }
}
