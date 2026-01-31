package domain.interfaces.patterns.chain;

import domain.anuncio.Anuncio;
import domain.exceptions.ModeracaoException;
import patterns.chain.BaseModeracaoHandler;

public class ValidadorTexto extends BaseModeracaoHandler {
    @Override
    public void handle(Anuncio anuncio) {
        System.out.println(">> Verificando Palavras Impróprias...");
        
        // Junta título e descrição para verificar tudo
        String texto = (anuncio.getTitulo() + " " + anuncio.getImovel().getDescricao()).toLowerCase();
        
        // Termos proibidos (exemplo simples)
        if (texto.contains("golpe") || texto.contains("fraude") || texto.contains("crime")) {
            throw new ModeracaoException("REPROVADO: O anúncio contém termos proibidos.");
        }

        super.handle(anuncio);
    }
}
