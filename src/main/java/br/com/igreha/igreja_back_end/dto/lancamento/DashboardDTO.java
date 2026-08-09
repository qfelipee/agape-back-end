package br.com.igreha.igreja_back_end.dto.lancamento;

import java.util.List;

public class DashboardDTO {
    private ResumoFinanceiroDTO resumo;
    private List<EvolucaoMensalDTO> evolucaoMensal;
    private List<FormaPagamentoDTO> distribuicaoEntradas;
    private List<FormaPagamentoDTO> distribuicaoSaidas;

    public DashboardDTO(ResumoFinanceiroDTO resumo, List<EvolucaoMensalDTO> evolucaoMensal,
                        List<FormaPagamentoDTO> distribuicaoEntradas, List<FormaPagamentoDTO> distribuicaoSaidas) {
        this.resumo = resumo;
        this.evolucaoMensal = evolucaoMensal;
        this.distribuicaoEntradas = distribuicaoEntradas;
        this.distribuicaoSaidas = distribuicaoSaidas;
    }

    public ResumoFinanceiroDTO getResumo() { return resumo; }
    public List<EvolucaoMensalDTO> getEvolucaoMensal() { return evolucaoMensal; }
    public List<FormaPagamentoDTO> getDistribuicaoEntradas() { return distribuicaoEntradas; }
    public List<FormaPagamentoDTO> getDistribuicaoSaidas() { return distribuicaoSaidas; }
}