package patterns.template;

import java.math.BigDecimal;

import domain.anuncio.Anuncio;

public abstract class ProcessoVenda {
    // O TEMPLATE METHOD
    public final String executarVenda(Anuncio anuncio) {
        
        // PASSO 1: Validação usando a String do estado
        // O método getEstadoAtual() retorna uma String (ex: "Ativo", "Rascunho")
        if (!anuncio.getEstadoAtual().equalsIgnoreCase("Ativo")) {
            return "ERRO: O anúncio não está ativo para venda. Status atual: " + anuncio.getEstadoAtual();
        }

        // PASSO 2: Cálculo do Valor (Varia nas subclasses)
        BigDecimal valorFinal = calcularValorFinal(anuncio.getValor());

        // PASSO 3: Processamento do Pagamento (Varia nas subclasses)
        boolean pagamentoAprovado = processarPagamento(valorFinal);

        if (pagamentoAprovado) {
            // PASSO 4: Finalização usando o Padrão State            
            try {
                anuncio.venderAnuncio(); 
                return gerarRecibo(anuncio, valorFinal);
            } catch (Exception e) {
                return "ERRO NA TRANSIÇÃO DE ESTADO: " + e.getMessage();
            }
        } else {
            return "FALHA: O pagamento foi recusado pela instituição financeira.";
        }
    }

    // --- MÉTODOS ABSTRATOS (Hooks) ---
    
    protected abstract BigDecimal calcularValorFinal(BigDecimal valorOriginal);

    protected abstract boolean processarPagamento(BigDecimal valor);

    protected abstract String getDescricaoPagamento();

    // --- Método Auxiliar ---
    protected String gerarRecibo(Anuncio anuncio, BigDecimal valorFinal) {
        return "=== VENDA CONCLUÍDA COM SUCESSO ===\n" +
               "Imóvel: " + anuncio.getTitulo() + "\n" +
               "Valor Original: R$ " + anuncio.getValor() + "\n" +
               "Forma de Pagamento: " + getDescricaoPagamento() + "\n" +
               "-----------------------------------\n" +
               "VALOR FINAL PAGO: R$ " + valorFinal;
    }
}
