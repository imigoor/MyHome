package service.anuncio.criar;

import domain.anuncio.Anuncio;
import domain.entities.Usuario;
import domain.enums.StatusAnuncio;
import domain.enums.TipoAnuncio;
import domain.imovel.Endereco;
import domain.imovel.Imovel;
import patterns.Prototype.AnuncioPrototypeRegistry;
import repository.anuncio.AnuncioRepository;

import java.math.BigDecimal;
import java.util.UUID;

public class CriarAnuncioPadraoUseCase implements ICriarAnuncioPadraoUseCase{
    private AnuncioRepository repository;
    private AnuncioPrototypeRegistry registry;

    public CriarAnuncioPadraoUseCase(AnuncioRepository repository) {
        this.repository = repository;
        this.registry = new AnuncioPrototypeRegistry();
    }

    @Override
    public Anuncio execute(Usuario usuario, String titulo, BigDecimal valor, TipoAnuncio tipoAnuncio, Endereco endereco, String chaveTemplateImovel) {
        Anuncio anuncio = new Anuncio();
        anuncio.setId(UUID.randomUUID());
        anuncio.setTitulo(titulo);
        anuncio.setValor(valor);
        anuncio.setTipoAnuncio(tipoAnuncio);
        anuncio.setAnunciante(usuario);
        anuncio.setStatus(StatusAnuncio.RASCUNHO);

        Imovel imovelClonado = registry.obterClone(chaveTemplateImovel);
        imovelClonado.setEndereco(endereco);

        anuncio.setImovel(imovelClonado);

        repository.salvar(anuncio);

        return anuncio;
    }
}
