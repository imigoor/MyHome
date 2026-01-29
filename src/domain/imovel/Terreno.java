package domain.imovel;

import domain.enums.TipoImovel;

public class Terreno extends Imovel {
    private boolean ehMurado;
    private String topografia;

    public Terreno(Endereco endereco, Double areaMetrosQuadrados,
                   Integer numeroQuartos, Integer numeroBanheiros,
                   String descricao, boolean ehMurado, String topografia) {
        super(endereco, areaMetrosQuadrados, numeroQuartos, numeroBanheiros, descricao);

        this.ehMurado = ehMurado;
        this.topografia = topografia;
    }

    public Terreno(Terreno terrenoTarget) {
        super(terrenoTarget);
        if (terrenoTarget != null) {
            this.ehMurado = terrenoTarget.ehMurado;
            this.topografia = terrenoTarget.topografia;
        }
    }

    @Override
    public Imovel clone() {
        return new Terreno(this); // A mágica acontece aqui!
    }

    public boolean isEhMurado() { return ehMurado; }
    public void setEhMurado(boolean ehMurado) { this.ehMurado = ehMurado; }

    public String getTopografia() { return topografia; }
    public void setTopografia(String topografia) { this.topografia = topografia; }

    @Override
    public String getTipoImovel() {
        return "TERRENO";
    }
}
