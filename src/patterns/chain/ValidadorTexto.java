package patterns.chain;

import domain.anuncio.Anuncio;
import domain.exceptions.ModeracaoException;
import patterns.singleton.Configuracao;

public class ValidadorTexto extends BaseModeracaoHandler {
    @Override
    public void handle(Anuncio anuncio) {       
        // Junta título e descrição para verificar tudo
        String texto = (anuncio.getTitulo() + " " + anuncio.getImovel().getDescricao()).toLowerCase();

        String termosStr = Configuracao.getInstance().getPropriedade("termos.proibidos");

        if (termosStr == null) {
            throw new RuntimeException("ERRO CRÍTICO: Configuração 'termos.proibidos' não encontrada no arquivo!");
        }
        
        // 2. Transforma "golpe,fraude,crime" em um array ["golpe", "fraude", "crime"]
        String[] termosProibidos = termosStr.split(",");
            
        // 3. Verifica cada termo
        for (String termo : termosProibidos) {
            if (texto.contains(termo.trim().toLowerCase())) {
                throw new ModeracaoException("REPROVADO: O anúncio contém termo proibido: " + termo);
            }
        }

        super.handle(anuncio);
    }
}
