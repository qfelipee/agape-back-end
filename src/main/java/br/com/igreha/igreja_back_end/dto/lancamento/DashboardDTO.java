package br.com.igreha.igreja_back_end.dto.lancamento;

import java.util.List;

public class DashboardDTO {
    private ResumoFinanceiroDTO resumo;
    private List<EvolucaoMensalDTO> evolucaoMensal;
    private List<FormaPagamentoDTO> distribuicaoFormaPagamento;

    public DashboardDTO(ResumoFinanceiroDTO resumo, List<EvolucaoMensalDTO> evolucaoMensal,
                        List<FormaPagamentoDTO> distribuicaoFormaPagamento) {
        this.resumo = resumo;
        this.evolucaoMensal = evolucaoMensal;
        this.distribuicaoFormaPagamento = distribuicaoFormaPagamento;
    }

    public ResumoFinanceiroDTO getResumo() { return resumo; }
    public List<EvolucaoMensalDTO> getEvolucaoMensal() { return evolucaoMensal; }
    public List<FormaPagamentoDTO> getDistribuicaoFormaPagamento() { return distribuicaoFormaPagamento; }
}