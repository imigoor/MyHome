package service.anuncio.moderacao;

import domain.anuncio.Anuncio;

public interface ISubmeterAnuncioUseCase {
    public String execute(Anuncio anuncio);
}
