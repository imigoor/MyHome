package domain.interfaces.patterns.state;

import domain.anuncio.Anuncio;
import domain.exceptions.ModeracaoException;

public abstract class EstadoAnuncio {
    public void enviarParaModeracao(Anuncio anuncio) { throw new ModeracaoException("Este anúncio não pode ser submetido para moderação no estado atual."); }
    public void aprovar(Anuncio anuncio) { throw new ModeracaoException("Este anúncio não pode ser aprovado no estado atual."); }
    public void reprovar(Anuncio anuncio) { throw new ModeracaoException("Este anúncio não pode ser reprovado no estado atual."); }
    public void vender(Anuncio anuncio) { throw new ModeracaoException("Este anúncio não pode ser marcado como vendido no estado atual."); }
    public void suspender(Anuncio anuncio) { throw new ModeracaoException("Este anúncio não pode ser suspenso no estado atual."); }
    public boolean jaEstaPublicado() { return false; }

    public abstract String getNomeEstado();
}
