package domain.imovel;

import domain.enums.TipoImovel;

public class Terreno extends Imovel {
    private boolean ehMurado;
    private String topografia;

    public Terreno(Long id, Endereco endereco, Double areaMetrosQuadrados,
                   Integer numeroQuartos, Integer numeroBanheiros,
                   String descricao, boolean ehMurado, String topografia) {
        super(id, endereco, areaMetrosQuadrados, numeroQuartos, numeroBanheiros, descricao);

        this.ehMurado = ehMurado;
        this.topografia = topografia;
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
