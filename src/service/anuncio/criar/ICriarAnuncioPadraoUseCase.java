package service.anuncio.criar;

import domain.anuncio.Anuncio;
import domain.entities.Usuario;
import domain.enums.TipoAnuncio;
import domain.imovel.Endereco;

import java.math.BigDecimal;

public interface ICriarAnuncioPadraoUseCase {
    Anuncio execute(Usuario usuario,
                    String titulo,
                    BigDecimal valor,
                    TipoAnuncio tipoAnuncio,
                    Endereco endereco,
                    String chaveTemplateImovel);
}
