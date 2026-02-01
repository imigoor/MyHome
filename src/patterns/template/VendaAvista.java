package patterns.template;

import java.math.BigDecimal;

public class VendaAvista extends ProcessoVenda {
    @Override
    protected BigDecimal calcularValorFinal(BigDecimal valorOriginal) {
        // Aplica 10% de desconto
        // Multiplica por 0.90 (que é 100% - 10%)
        return valorOriginal.multiply(new BigDecimal("0.90"));
    }

    @Override
    protected boolean processarPagamento(BigDecimal valor) {
        // Simulação: Valida se o PIX ou Transferência foi recebido
        return true; 
    }

    // Retorna a descrição do pagamento
    @Override
    protected String getDescricaoPagamento() {
        return "À Vista (PIX/Dinheiro) - 10% de Desconto";
    }
}
