package service.anuncio.moderacao;

import domain.anuncio.Anuncio;
import domain.exceptions.ModeracaoException;
import domain.interfaces.patterns.chain.ModeracaoHandler;
import domain.interfaces.patterns.chain.ValidadorDescricao;
import domain.interfaces.patterns.chain.ValidadorPreco;
import domain.interfaces.patterns.chain.ValidadorTexto;

public class SubmeterAnuncioUseCase implements ISubmeterAnuncioUseCase {

    public SubmeterAnuncioUseCase() {

    }

    @Override
    public String execute(Anuncio anuncio) {
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
