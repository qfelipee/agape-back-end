package br.com.igreha.igreja_back_end.dto.lancamento;

import java.math.BigDecimal;

public class FormaPagamentoDTO {
    private String formaPagamento;
    private BigDecimal total;

    public FormaPagamentoDTO(String formaPagamento, BigDecimal total) {
        this.formaPagamento = formaPagamento;
        this.total = total;
    }

    public String getFormaPagamento() { return formaPagamento; }
    public BigDecimal getTotal() { return total; }
}