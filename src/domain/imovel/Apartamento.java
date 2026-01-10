package domain.imovel;

public class Apartamento extends Imovel {
    private int andar;
    private boolean possuiElevador;
    private boolean portaria24h;
    private Double valorCondominio;

    public Apartamento(Long id, Endereco endereco, Double areaMetrosQuadrados,
                       Integer numeroQuartos, Integer numeroBanheiros,
                       String descricao, int andar, boolean possuiElevador,
                       boolean portaria24h, Double valorCondominio) {
        super(id, endereco, areaMetrosQuadrados, numeroQuartos, numeroBanheiros, descricao);

        this.andar = andar;
        this. possuiElevador = possuiElevador;
        this.portaria24h = portaria24h;
        this.valorCondominio = valorCondominio;
    }

    public int getAndar(){ return this.andar; }
    public void setAndar(int andar) { this.andar = andar; }

    public boolean getPossuiElevador() { return this.possuiElevador; }
    public void setPossuiElevador(boolean possuiElevador) { this.possuiElevador = possuiElevador; }

    public boolean getPortaria24h() { return this.portaria24h; }
    public void setPortaria24h(boolean portaria24h) { this.portaria24h = portaria24h; }

    public Double getValorCondominio() { return this.valorCondominio; }
    public void setValorCondominio(Double valorCondominio) { this.valorCondominio = valorCondominio; }
}
