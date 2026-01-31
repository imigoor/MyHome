package domain.interfaces.patterns.chain;

import domain.anuncio.Anuncio;
import domain.exceptions.ModeracaoException;
import patterns.chain.BaseModeracaoHandler;

public class ValidadorDescricao extends BaseModeracaoHandler {
    @Override
    public void handle(Anuncio anuncio) {
        System.out.println(">> Verificando Tamanho da Descrição...");

        String descricao = anuncio.getImovel().getDescricao();
        
        if (descricao == null || descricao.trim().length() < 10) {
            throw new ModeracaoException("REPROVADO: A descrição é muito curta (mínimo 10 caracteres).");
        }

        super.handle(anuncio);
    }
}
