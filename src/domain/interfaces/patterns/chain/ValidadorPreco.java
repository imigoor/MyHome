package domain.interfaces.patterns.chain;

import java.math.BigDecimal;

import domain.anuncio.Anuncio;
import domain.exceptions.ModeracaoException;
import patterns.chain.BaseModeracaoHandler;

public class ValidadorPreco extends BaseModeracaoHandler {
    @Override
    public void handle(Anuncio anuncio) {
        System.out.println(">> Verificando Preço...");

        if (anuncio.getValor() == null || anuncio.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ModeracaoException("REPROVADO: O valor do anúncio deve ser maior que zero.");
        }
        
        // Se passou, chama o próximo (lógica do pai)
        super.handle(anuncio);
    }
}
