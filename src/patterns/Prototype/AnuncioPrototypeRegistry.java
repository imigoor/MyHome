package patterns.Prototype;

import domain.anuncio.Anuncio;
import domain.imovel.Apartamento;
import domain.imovel.Casa;
import domain.imovel.Imovel;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


public class AnuncioPrototypeRegistry {
    private Map<String, Imovel> prototipos;

    public AnuncioPrototypeRegistry() {
        prototipos = new HashMap<>();
        carregarTemplates();
    }

    private void carregarTemplates() {
        Casa casa = new Casa();
        casa.setDescricao("Casa Padrão (3 Quartos/Quintal)");
        casa.setAreaMetrosQuadrados(90.0);
        casa.setNumeroQuartos(3);
        casa.setNumeroBanheiros(2);
        casa.setPossuiQuintal(true);
        casa.setPossuiPiscina(false);
        casa.setVagasGaragem(1);
        prototipos.put("CASA_PADRAO", casa);

        Apartamento apt = new Apartamento();
        apt.setDescricao("Apartamento Padrão (2 Quartos/60m)");
        apt.setAreaMetrosQuadrados(60.0);
        apt.setNumeroQuartos(2);
        apt.setNumeroBanheiros(1);
        apt.setAndar(10);
        apt.setPossuiElevador(true);
        apt.setPortaria24h(true);
        apt.setValorCondominio(200.0);
        prototipos.put("APTO_PADRAO", apt);
    }

    public Imovel obterClone(String chave) {
        Imovel prototipo = prototipos.get(chave);
        if (prototipo == null)
            throw new IllegalArgumentException("Modelo não encontrado");
        return prototipo.clone();
    }
}
