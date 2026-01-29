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
        Imovel imovelClonado = registry.obterClone(chaveTemplateImovel);
        imovelClonado.setEndereco(endereco);

        Anuncio anuncio = new Anuncio(titulo, valor, tipoAnuncio, imovelClonado, usuario);

        anuncio.setImovel(imovelClonado);

        repository.salvar(anuncio);

        return anuncio;
    }
}
