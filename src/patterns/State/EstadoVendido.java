package patterns.State;

import domain.interfaces.patterns.state.EstadoAnuncio;

public class EstadoVendido extends EstadoAnuncio {
    @Override
    public boolean jaEstaPublicado() {
        return true;
    }
    
    @Override
    public String getNomeEstado() {
        return "VENDIDO";
    }
}
