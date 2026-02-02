package service.anuncio.criar;

import domain.anuncio.Anuncio;
import domain.entities.Usuario;
import domain.enums.TipoAnuncio;
import domain.imovel.Imovel;
import domain.interfaces.patterns.observer.AnuncioObserver;
import patterns.factory.ApartamentoFactory;
import patterns.factory.CasaFactory;
import patterns.factory.ImovelFactory;
import patterns.factory.TerrenoFactory;
import repository.anuncio.AnuncioRepository;

import java.math.BigDecimal;
import java.util.Map;

public class CriarAnuncioUseCase implements ICriarAnuncioUseCase{
    private AnuncioRepository anuncioRepository;
    private final AnuncioObserver anuncioObserver;

    public CriarAnuncioUseCase(
            AnuncioRepository anuncioRepository,
            AnuncioObserver anuncioObserver) {
        this.anuncioRepository = anuncioRepository;
        this.anuncioObserver = anuncioObserver;
    }

    @Override
    public Anuncio execute(Usuario usuario, String titulo, BigDecimal valor,
                           TipoAnuncio tipoAnuncio, String tipoImovel,
                         Map<String, Object> dadosImovel) {
        if (!usuario.podeAnunciar()) {
            throw new SecurityException("Usuário não tem permissão para anunciar.");
        }

        ImovelFactory imovelFactory = selecionarFabrica(tipoImovel);
        Imovel imovel = imovelFactory.criarImovel(dadosImovel);

        Anuncio anuncio = new Anuncio(titulo, valor, tipoAnuncio, imovel, usuario);
        anuncio.addObserver(anuncioObserver);

        anuncioRepository.salvar(anuncio);

        return anuncio;
    }

    private ImovelFactory selecionarFabrica(String tipo) {
        switch (tipo.toUpperCase()) {
            case "CASA":
                return new CasaFactory();
            case "APARTAMENTO":
                return new ApartamentoFactory();
            case "TERRENO":
                return new TerrenoFactory();
            default:
                throw new IllegalArgumentException("Tipo de imóvel '" + tipo + "' não é suportado pelo sistema.");
        }
    }
}
