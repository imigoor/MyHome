package patterns.chain;

import domain.anuncio.Anuncio;
import domain.exceptions.ModeracaoException;
import patterns.singleton.Configuracao;

public class ValidadorTamanho extends BaseModeracaoHandler {
    @Override
    public void handle(Anuncio anuncio) {        
        // --- 1. VALIDAÇÃO DO TÍTULO ---
        String minTituloStr = Configuracao.getInstance().getPropriedade("anuncio.titulo.min");

        if (minTituloStr == null) {
            throw new RuntimeException("ERRO CRÍTICO: Configuração 'anuncio.titulo.min' não encontrada no arquivo!");
        }

        int minTitulo = Integer.parseInt(minTituloStr);
        
        if (anuncio.getTitulo() == null || anuncio.getTitulo().trim().length() < minTitulo) {
            throw new ModeracaoException("REPROVADO: O título é muito curto (mínimo " + minTitulo + " caracteres).");
        }

        // --- 2. VALIDAÇÃO DA DESCRIÇÃO ---        
        String minDescStr = Configuracao.getInstance().getPropriedade("anuncio.descricao.min");

        if (minDescStr == null) {
            throw new RuntimeException("ERRO CRÍTICO: Configuração 'anuncio.descricao.min' não encontrada no arquivo!");
        }

        int minDesc = Integer.parseInt(minDescStr);

        String descricao = anuncio.getImovel().getDescricao();
        
        if (descricao == null || descricao.trim().length() < minDesc) {
            throw new ModeracaoException("REPROVADO: A descrição é muito curta (mínimo " + minDesc + " caracteres).");
        }

        super.handle(anuncio);
    }
}
