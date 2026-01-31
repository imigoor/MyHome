package patterns.State;

import domain.anuncio.Anuncio;
import domain.interfaces.patterns.state.EstadoAnuncio;

public class EstadoModeracao extends EstadoAnuncio {
    @Override
    public void aprovar(Anuncio anuncio) {
        anuncio.mudarEstadoAnuncio(new EstadoAtivo());
    }

    @Override
    public void reprovar(Anuncio anuncio) {
        anuncio.mudarEstadoAnuncio(new EstadoSuspenso());
    }

    @Override
    public String getNomeEstado() {
        return "MODERAÇÃO";
    }
}
