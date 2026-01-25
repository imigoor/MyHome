package service.anuncio.criar;

import domain.anuncio.Anuncio;
import domain.entities.Usuario;
import domain.enums.StatusAnuncio;
import domain.enums.TipoAnuncio;
import domain.imovel.Imovel;
import patterns.factory.ApartamentoFactory;
import patterns.factory.CasaFactory;
import patterns.factory.ImovelFactory;
import patterns.factory.TerrenoFactory;
import repository.anuncio.AnuncioRepository;

import java.math.BigDecimal;
import java.util.Map;

public class CriarAnuncioUseCase implements ICriarAnuncioUseCase{
    private AnuncioRepository anuncioRepository;

    public CriarAnuncioUseCase(AnuncioRepository anuncioRepository){
        this.anuncioRepository = anuncioRepository;
    }

    @Override
    public Anuncio execute(Usuario usuario, String titulo, BigDecimal valor,
                         String tipoTransacaoStr, String tipoImovel,
                         Map<String, Object> dadosImovel) {
        if (!usuario.podeAnunciar()) {
            throw new SecurityException("Usuário não tem permissão para anunciar.");
        }

        ImovelFactory imovelFactory = selecionarFabrica(tipoImovel);
        Imovel imovel = imovelFactory.criarImovel(dadosImovel);

        Anuncio anuncio = new Anuncio();
        anuncio.setTitulo(titulo);
        anuncio.setValor(valor);
        anuncio.setTipoAnuncio(TipoAnuncio.valueOf(tipoTransacaoStr));
        anuncio.setImovel(imovel);
        anuncio.setAnunciante(usuario);
        anuncio.setStatus(StatusAnuncio.RASCUNHO);

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
