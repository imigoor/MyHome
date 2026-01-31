package service.anuncio.moderacao;

import domain.anuncio.Anuncio;
import domain.enums.StatusAnuncio;
import domain.exceptions.ModeracaoException;
import domain.interfaces.patterns.chain.ModeracaoHandler;
import domain.interfaces.patterns.chain.ValidadorDescricao;
import domain.interfaces.patterns.chain.ValidadorPreco;
import domain.interfaces.patterns.chain.ValidadorTexto;
import repository.anuncio.AnuncioRepository;

public class SubmeterAnuncioUseCase {
    private AnuncioRepository anuncioRepository;

    public SubmeterAnuncioUseCase(AnuncioRepository anuncioRepository) {
        this.anuncioRepository = anuncioRepository;
    }

    public void execute(Anuncio anuncio) {
        System.out.println("\n--- Iniciando Processo de Moderação ---");
        
        // 1. Instanciar os Validadores (Handlers)
        ModeracaoHandler validadorPreco = new ValidadorPreco();
        ModeracaoHandler validadorTexto = new ValidadorTexto();
        ModeracaoHandler validadorDescricao = new ValidadorDescricao();

        // 2. Montar a Corrente (Chain): Preco -> Texto -> Descricao
        validadorPreco.setNext(validadorTexto);
        validadorTexto.setNext(validadorDescricao);

        try {
            // Muda status para "Em análise"
            anuncio.setStatus(StatusAnuncio.EM_MODERACAO);

            // 3. Inicia a validação pelo PRIMEIRO da fila
            validadorPreco.handle(anuncio);

            // Se chegou aqui, não houve erro
            anuncio.setStatus(StatusAnuncio.ATIVO);
            System.out.println("SUCESSO: Anuncio Aprovado e Publicado!");

        } catch (ModeracaoException e) {
            // Se algum handler reclamou
            anuncio.setStatus(StatusAnuncio.SUSPENSO);
            System.err.println("FALHA NA MODERAÇÃO: " + e.getMessage());
        }
        
        System.out.println("Status Final do Anúncio: " + anuncio.getStatus());
    }
}
