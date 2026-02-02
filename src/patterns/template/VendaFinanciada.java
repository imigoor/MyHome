package patterns.template;

import java.math.BigDecimal;

public class VendaFinanciada extends ProcessoVenda {
    @Override
    protected BigDecimal calcularValorFinal(BigDecimal valorOriginal) {
        // Aplica 20% de juros
        // Multiplica por 1.20 (que é 100% + 20%)
        return valorOriginal.multiply(new BigDecimal("1.20"));
    }

    @Override
    protected boolean processarPagamento(BigDecimal valor) {
        // Simulação: Envia solicitação ao banco e aguarda aprovação de crédito
        return true;
    }

    // Retorna a descrição do pagamento
    @Override
    protected String getDescricaoPagamento() {
        return "Financiamento Bancário - Juros Totais de 20%";
    }
}
