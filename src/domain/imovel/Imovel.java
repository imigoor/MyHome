package domain.imovel;

import domain.enums.TipoImovel;
import domain.interfaces.patterns.prototype.Prototype;

public abstract class Imovel implements Prototype<Imovel> {
    private Long id;
    private Endereco endereco;
    private Double areaMetrosQuadrados;
    private Integer numeroQuartos;
    private Integer numeroBanheiros;
    private String descricao;

    public Imovel() {}

    public Imovel(Long id, Endereco endereco, Double areaMetrosQuadrados, Integer numeroQuartos, Integer numeroBanheiros, String descricao) {
        this.id = id; // criei esse atributo pensando que seria gerado via banco, mas como iremos simular banco verificar se fazz sentido esse atributo
        this.endereco = endereco;
        this.areaMetrosQuadrados = areaMetrosQuadrados;
        this.numeroQuartos = numeroQuartos;
        this.numeroBanheiros = numeroBanheiros;
        this.descricao = descricao;
        // this.tipoImovel = tipoImovel; // verificar com professor se continuamos com ENUM para TipoImovel
        // ou a ideia de metodo gancho (afim de evitar mexer em codigo existente quando for criar um novo tipo de imovell)
    }

    public Imovel(Imovel imovelTarget) {
        if (imovelTarget != null) {
            // regra: clone nasce sem ID (será gerado ao salvar)
            this.id = null;

            this.areaMetrosQuadrados = imovelTarget.areaMetrosQuadrados;
            this.numeroQuartos = imovelTarget.numeroQuartos;
            this.numeroBanheiros = imovelTarget.numeroBanheiros;
            this.descricao = imovelTarget.descricao;

            // Cuidado: Endereco é objeto, precisamos clonar também se não for null
            // (Assumindo que Endereco é simples ou imutável por enquanto)
            this.endereco = imovelTarget.endereco;
        }
    }

    @Override
    public abstract Imovel clone();

    public abstract String getTipoImovel();

    public Long getId() { return id; }
    public void setId(Long id) {this.id = id;}

    public Endereco getEndereco() { return endereco; }
    public void setEndereco(Endereco endereco) { this.endereco = endereco; }

    public Double getAreaMetrosQuadrados() { return areaMetrosQuadrados; }
    public void setAreaMetrosQuadrados(Double areaMetrosQuadrados) { this.areaMetrosQuadrados = areaMetrosQuadrados; }

    public Integer getNumeroQuartos() { return numeroQuartos; }
    public void setNumeroQuartos(Integer numeroQuartos) { this.numeroQuartos = numeroQuartos; }

    public Integer getNumeroBanheiros() { return numeroBanheiros; }
    public void setNumeroBanheiros(Integer numeroBanheiros) { this.numeroBanheiros = numeroBanheiros; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}
