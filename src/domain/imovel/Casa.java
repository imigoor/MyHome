package domain.imovel;

import domain.enums.TipoImovel;

public class Casa extends Imovel {
    private boolean possuiQuintal;
    private boolean possuiPiscina;
    private int vagasGaragem;

    public Casa(Long id, Endereco endereco, Double areaMetrosQuadrados,
                Integer numeroQuartos, Integer numeroBanheiros, String descricao,
                boolean possuiQuintal, boolean possuiPiscina, int vagasGaragem) {
        super(id, endereco, areaMetrosQuadrados, numeroQuartos, numeroBanheiros, descricao);

        this.possuiQuintal = possuiQuintal;
        this.possuiPiscina = possuiPiscina;
        this.vagasGaragem = vagasGaragem;
    }

    public boolean isPossuiQuintal() { return possuiQuintal; }
    public void setPossuiQuintal(boolean possuiQuintal) { this.possuiQuintal = possuiQuintal; }

    public boolean isPossuiPiscina() { return possuiPiscina; }
    public void setPossuiPiscina(boolean possuiPiscina) { this.possuiPiscina = possuiPiscina; }

    public int getVagasGaragem() { return vagasGaragem; }
    public void setVagasGaragem(int vagasGaragem) { this.vagasGaragem = vagasGaragem; }

    @Override
    public String getTipoImovel() {
        return "CASA";
    }
}
