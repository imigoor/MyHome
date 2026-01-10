package domain.imovel;

public class Imovel {
    private Long id;
    private Endereco endereco;
    private Double areaMetrosQuadrados;
    private Integer numeroQuartos;
    private Integer numeroBanheiros;
    private String descricao;

    public Imovel(Long id, Endereco endereco, Double areaMetrosQuadrados, Integer numeroQuartos, Integer numeroBanheiros, String descricao) {
        this.id = id;
        this.endereco = endereco;
        this.areaMetrosQuadrados = areaMetrosQuadrados;
        this.numeroQuartos = numeroQuartos;
        this.numeroBanheiros = numeroBanheiros;
        this.descricao = descricao;
    }

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
