package br.com.igreha.igreja_back_end.mapper.lancamento;

import br.com.igreha.igreja_back_end.dto.lancamento.LancamentoResponseDTO;
import br.com.igreha.igreja_back_end.entity.lancamento.Lancamento;
import org.springframework.stereotype.Component;

@Component
public class LancamentoMapper {

    public LancamentoResponseDTO toResponseDTO(Lancamento lancamento) {
        return new LancamentoResponseDTO(
                lancamento.getId(),
                lancamento.getTipo().name(),
                lancamento.getDescricao(),
                lancamento.getValor(),
                lancamento.getFormaPagamento(),
                lancamento.getDtHoraLancado(),
                lancamento.getUser().getId(),
                lancamento.getUser().getNome()
        );
    }
}