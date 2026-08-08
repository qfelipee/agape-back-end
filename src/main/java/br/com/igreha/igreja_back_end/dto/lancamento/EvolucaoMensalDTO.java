package br.com.igreha.igreja_back_end.dto.lancamento;

import java.math.BigDecimal;

public class EvolucaoMensalDTO {
    private String mes; // formato "2026-08"
    private BigDecimal entradas;
    private BigDecimal saidas;

    public EvolucaoMensalDTO(String mes, BigDecimal entradas, BigDecimal saidas) {
        this.mes = mes;
        this.entradas = entradas;
        this.saidas = saidas;
    }

    public String getMes() { return mes; }
    public BigDecimal getEntradas() { return entradas; }
    public BigDecimal getSaidas() { return saidas; }
}