package service.anuncio.comercial;

import domain.anuncio.Anuncio;
import patterns.template.ProcessoVenda;

// Interface para o Caso de Uso de Realizar Venda
public interface IRealizarVendaUseCase {
    String vender(Anuncio anuncio, ProcessoVenda processoVenda);
}
