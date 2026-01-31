package service.anuncio.buscar;

import domain.interfaces.patterns.decorator.IBuscaAnuncio;
import patterns.decorator.*;

import java.math.BigDecimal;

public class FiltroFactory {

    public static IBuscaAnuncio criar(String tipoFiltro, IBuscaAnuncio buscaAtual, Object valor) {

        switch (tipoFiltro) {
            case "cidade":
                return new FiltroCidade(buscaAtual, (String) valor);

            case "tipoImovel":
                return new FiltroTipoImovel(buscaAtual, (String) valor);

            case "precoMaximo":
                return new FiltroPrecoMaximo(buscaAtual, (BigDecimal) valor);

            default:
                // não faz nada, apenas retorna busca atual (a gente tem segurança na parte da interface)
                return buscaAtual;
        }
    }
}