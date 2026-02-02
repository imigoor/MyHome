package patterns.State;

import domain.anuncio.Anuncio;
import domain.interfaces.patterns.state.EstadoAnuncio;

public class EstadoSuspenso extends EstadoAnuncio {
    @Override
    public void enviarParaModeracao(Anuncio anuncio) {
        anuncio.mudarEstadoAnuncio(new EstadoModeracao());
    }

    @Override
    public String getNomeEstado() {
        return "SUSPENSO";
    }
}
