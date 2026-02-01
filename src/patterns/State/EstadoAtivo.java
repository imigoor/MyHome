package patterns.State;

import domain.anuncio.Anuncio;
import domain.interfaces.patterns.state.EstadoAnuncio;

public class EstadoAtivo extends EstadoAnuncio {
    @Override
    public void vender(Anuncio anuncio) {
        anuncio.mudarEstadoAnuncio(new EstadoVendido());
    }

    @Override
    public void suspender(Anuncio anuncio) {
        anuncio.mudarEstadoAnuncio(new EstadoSuspenso());
    }

    @Override
    public boolean jaEstaPublicado() {
        return true;
    }

    @Override
    public String getNomeEstado() {
        return "ATIVO";
    }
}
