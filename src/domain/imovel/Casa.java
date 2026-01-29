package domain.imovel;

import domain.enums.TipoImovel;

public class Casa extends Imovel {
    private boolean possuiQuintal;
    private boolean possuiPiscina;
    private int vagasGaragem;

    public Casa(){}

    public Casa(Endereco endereco, Double areaMetrosQuadrados,
                Integer numeroQuartos, Integer numeroBanheiros, String descricao,
                boolean possuiQuintal, boolean possuiPiscina, int vagasGaragem) {
        super(endereco, areaMetrosQuadrados, numeroQuartos, numeroBanheiros, descricao);

        this.possuiQuintal = possuiQuintal;
        this.possuiPiscina = possuiPiscina;
        this.vagasGaragem = vagasGaragem;
    }

    public Casa(Casa target) {
        super(target); // Pai copia quartos, area, etc.
        if (target != null) {
            this.possuiQuintal = target.possuiQuintal;
            this.possuiPiscina = target.possuiPiscina;
            this.vagasGaragem = target.vagasGaragem;
        }
    }

    @Override
    public Imovel clone() {
        return new Casa(this); // A mágica acontece aqui!
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
