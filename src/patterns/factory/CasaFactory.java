package patterns.factory;

import domain.imovel.Casa;
import domain.imovel.Endereco;
import domain.imovel.Imovel;

import java.util.Map;

public class CasaFactory extends ImovelFactory {

    @Override
    public Imovel criarImovel(Map<String, Object> dados) {
        return new Casa(
                null, // ID (decidir por onde iremos gerar)
                (Endereco) dados.get("endereco"),
                (Double) dados.get("area"),
                (Integer) dados.get("quartos"),
                (Integer) dados.get("banheiros"),
                (String) dados.get("descricao"),
                (Boolean) dados.get("quintal"),
                (Boolean) dados.get("piscina"),
                (Integer) dados.get("vagas")
        );
    }
}
