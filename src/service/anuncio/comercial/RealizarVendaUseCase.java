package service.anuncio.comercial;

import domain.anuncio.Anuncio;
import patterns.template.ProcessoVenda;

public class RealizarVendaUseCase implements IRealizarVendaUseCase {
    @Override
    public String vender(Anuncio anuncio, ProcessoVenda processoVenda) {
        // Chama o Template Method 
        return processoVenda.executarVenda(anuncio);
    }
}
