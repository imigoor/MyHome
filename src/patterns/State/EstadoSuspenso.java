package patterns.State;

import domain.interfaces.patterns.state.EstadoAnuncio;

public class EstadoSuspenso extends EstadoAnuncio {
    @Override
    public String getNomeEstado() {
        return "SUSPENSO";
    }
}
