package br.com.igreha.igreja_back_end.dto.lancamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LancamentoResponseDTO {

    private Integer id;
    private String tipo;
    private String descricao;
    private BigDecimal valor;
    private String formaPagamento;
    private LocalDateTime dtHoraLancado;
    private Integer userId;
    private String userNome;

    public LancamentoResponseDTO(Integer id, String tipo, String descricao, BigDecimal valor,
                                 String formaPagamento, LocalDateTime dtHoraLancado,
                                 Integer userId, String userNome) {
        this.id = id;
        this.tipo = tipo;
        this.descricao = descricao;
        this.valor = valor;
        this.formaPagamento = formaPagamento;
        this.dtHoraLancado = dtHoraLancado;
        this.userId = userId;
        this.userNome = userNome;
    }

    public Integer getId() { return id; }
    public String getTipo() { return tipo; }
    public String getDescricao() { return descricao; }
    public BigDecimal getValor() { return valor; }
    public String getFormaPagamento() { return formaPagamento; }
    public LocalDateTime getDtHoraLancado() { return dtHoraLancado; }
    public Integer getUserId() { return userId; }
    public String getUserNome() { return userNome; }
}