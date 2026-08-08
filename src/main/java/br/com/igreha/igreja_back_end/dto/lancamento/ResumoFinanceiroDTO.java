package br.com.igreha.igreja_back_end.dto.lancamento;

import java.math.BigDecimal;

public class ResumoFinanceiroDTO {
    private BigDecimal totalEntradas;
    private BigDecimal totalSaidas;
    private BigDecimal saldoFinal;

    public ResumoFinanceiroDTO(BigDecimal totalEntradas, BigDecimal totalSaidas, BigDecimal saldoFinal) {
        this.totalEntradas = totalEntradas;
        this.totalSaidas = totalSaidas;
        this.saldoFinal = saldoFinal;
    }

    public BigDecimal getTotalEntradas() { return totalEntradas; }
    public BigDecimal getTotalSaidas() { return totalSaidas; }
    public BigDecimal getSaldoFinal() { return saldoFinal; }
}