package patterns.factory;

import domain.imovel.Apartamento;
import domain.imovel.Endereco;
import domain.imovel.Imovel;

import java.util.Map;

public class ApartamentoFactory extends ImovelFactory {
    @Override
    public Imovel criarImovel(Map<String, Object> dados) {
        return new Apartamento(
                null, // ID (gerado depois)
                (Endereco) dados.get("endereco"),
                (Double) dados.get("area"),
                (Integer) dados.get("quartos"),
                (Integer) dados.get("banheiros"),
                (String) dados.get("descricao"),
                // Atributos específicos de Apartamento:
                (Integer) dados.get("andar"),
                (Boolean) dados.get("elevador"),
                (Boolean) dados.get("portaria"),
                (Double) dados.get("condominio")
        );
    }
}
