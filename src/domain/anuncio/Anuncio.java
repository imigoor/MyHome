package domain.anuncio;

import domain.entities.Usuario;
import domain.enums.TipoAnuncio;
import domain.imovel.Imovel;
import domain.interfaces.patterns.observer.AnuncioObserver;
import domain.interfaces.patterns.state.EstadoAnuncio;
import patterns.State.EstadoRascunho;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Anuncio {
    private UUID id;
    private String titulo;
    private BigDecimal valor;
    private LocalDateTime dataCriacao;

    private TipoAnuncio tipoAnuncio;
    private EstadoAnuncio estadoAtual;

    private Imovel imovel;
    private Usuario anunciante;

    private final List<AnuncioObserver> observers = new ArrayList<>();

    public Anuncio(String titulo, BigDecimal valor, TipoAnuncio tipoAnuncio, Imovel imovel, Usuario usuario){
        this.id = UUID.randomUUID();
        this.titulo = titulo;
        this.valor = valor;
        this.tipoAnuncio = tipoAnuncio;
        this.imovel = imovel;
        this.anunciante = usuario;

        this.estadoAtual = new EstadoRascunho();
        this.dataCriacao = LocalDateTime.now();
    }

    // Observer
    public void addObserver(AnuncioObserver observer) {
        this.observers.add(observer);
    }

    private void notificarObservers(String estadoAnterior, String estadoAtual) {
        for (AnuncioObserver observer : observers) {
            observer.onEstadoAlterado(this, estadoAnterior, estadoAtual);
        }
    }

    // State
    public void mudarEstadoAnuncio(EstadoAnuncio novoEstado){
        String estadoAnterior = this.estadoAtual.getNomeEstado();
        this.estadoAtual = novoEstado;
        notificarObservers(estadoAnterior, novoEstado.getNomeEstado());
    }

    public void submeterAModeracao(){
        this.estadoAtual.enviarParaModeracao(this);
    }

    public void aprovarModeracao(){
        this.estadoAtual.aprovar(this);
    }

    public void reprovarModeracao(){
        this.estadoAtual.reprovar(this);
    }

    public void venderAnuncio() {
        this.estadoAtual.vender(this);
    }

    public void suspenderAnuncio() {
        this.estadoAtual.suspender(this);
    }

    public boolean jaEstaPublicado() {
        return this.estadoAtual.jaEstaPublicado();
    }

    // Getters e Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public TipoAnuncio getTipoAnuncio() { return tipoAnuncio; }
    public void setTipoAnuncio(TipoAnuncio tipoAnuncio) { this.tipoAnuncio = tipoAnuncio; }

    public String getEstadoAtual() { return estadoAtual.getNomeEstado(); }

    // Método deve ser utilizado apenas na criação de anúncios obtidos da planilha CSV, para aparecer ans buscas de testes
    public void setEstadoAtual(EstadoAnuncio estadoAtual) {
        this.estadoAtual = estadoAtual;
    }

    public Imovel getImovel() { return imovel; }
    public void setImovel(Imovel imovel) { this.imovel = imovel; }

    public Usuario getAnunciante() { return anunciante; }
    public void setAnunciante(Usuario anunciante) { this.anunciante = anunciante; }
}
