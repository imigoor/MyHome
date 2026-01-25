package patterns.factory;

import domain.imovel.Endereco;
import domain.imovel.Imovel;
import domain.imovel.Terreno;

import java.util.Map;

public class TerrenoFactory extends ImovelFactory {
    @Override
    public Imovel criarImovel(Map<String, Object> dados) {
        return new Terreno(
                null, // ID
                (Endereco) dados.get("endereco"),
                (Double) dados.get("area"),
                0,
                0,
                (String) dados.get("descricao"),
                (Boolean) dados.get("murado"),
                (String) dados.get("topografia")
        );
    }
}
