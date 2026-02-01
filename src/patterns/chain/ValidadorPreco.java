package patterns.chain;

import java.math.BigDecimal;

import domain.anuncio.Anuncio;
import domain.exceptions.ModeracaoException;
import patterns.singleton.Configuracao;

public class ValidadorPreco extends BaseModeracaoHandler {
    @Override
    public void handle(Anuncio anuncio) {        
        // 1. Ler o preço mínimo do arquivo
        String precoMinStr = Configuracao.getInstance().getPropriedade("anuncio.preco.min");

        if (precoMinStr == null) {
            throw new RuntimeException("ERRO CRÍTICO: Configuração 'anuncio.preco.min' não encontrada no arquivo!");
        }

        double precoMinDouble = Double.parseDouble(precoMinStr);
        BigDecimal precoMinimo = BigDecimal.valueOf(precoMinDouble);

        if (anuncio.getValor() == null || anuncio.getValor().compareTo(precoMinimo) <= 0) {
            throw new ModeracaoException("REPROVADO: O valor deve ser maior que R$ " + precoMinDouble);
        }
        
        // Se passou, chama o próximo (lógica do pai)
        super.handle(anuncio);
    }
}
