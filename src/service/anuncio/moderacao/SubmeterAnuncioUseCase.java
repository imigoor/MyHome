package service.anuncio.moderacao;

import domain.anuncio.Anuncio;
import domain.exceptions.ModeracaoException;
import domain.interfaces.patterns.chain.IModeracaoHandler;
import patterns.chain.ValidadorTamanho;
import patterns.chain.ValidadorPreco;
import patterns.chain.ValidadorTexto;

public class SubmeterAnuncioUseCase implements ISubmeterAnuncioUseCase {

    public SubmeterAnuncioUseCase() {

    }

    @Override
    public String execute(Anuncio anuncio) {        
        // 1. Instanciar os Validadores (Handlers)
        IModeracaoHandler validadorPreco = new ValidadorPreco();
        IModeracaoHandler validadorTexto = new ValidadorTexto();
        IModeracaoHandler validadorTamanho = new ValidadorTamanho();

        // 2. Montar a Corrente (Chain): Preco -> Texto -> tamanho
        validadorPreco.setNext(validadorTexto);
        validadorTexto.setNext(validadorTamanho);

        try {
            // Muda status para "Em análise"
            anuncio.submeterAModeracao();

            // 3. Inicia a validação pelo PRIMEIRO da fila
            validadorPreco.handle(anuncio);

            // Se chegou aqui, não houve erro
            anuncio.aprovarModeracao();
            return "SUCESSO: Anuncio Aprovado e Publicado! \nStatus Final do Anúncio: " + anuncio.getEstadoAtual();

        } catch (ModeracaoException e) {
            // Se algum handler reclamou
            anuncio.reprovarModeracao();
            return "FALHA NA MODERAÇÃO: " + e.getMessage();
        }
    }
}
