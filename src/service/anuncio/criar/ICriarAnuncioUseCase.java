package service.anuncio.criar;

import domain.anuncio.Anuncio;
import domain.entities.Usuario;

import java.math.BigDecimal;
import java.util.Map;

public interface ICriarAnuncioUseCase {
    Anuncio execute(Usuario usuario, String titulo, BigDecimal valor,
                     String tipoTransacaoStr, String tipoImovel,
                     Map<String, Object> dadosImovel);
}
