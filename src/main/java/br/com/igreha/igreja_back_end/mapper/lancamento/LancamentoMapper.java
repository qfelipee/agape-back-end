package br.com.igreha.igreja_back_end.mapper.lancamento;

import br.com.igreha.igreja_back_end.dto.lancamento.LancamentoResponseDTO;
import br.com.igreha.igreja_back_end.entity.lancamento.Lancamento;
import org.springframework.stereotype.Component;

@Component
public class LancamentoMapper {

    public LancamentoResponseDTO toResponseDTO(Lancamento lancamento) {
        boolean usuarioExiste = lancamento.getUser() != null;

        return new LancamentoResponseDTO(
                lancamento.getId(),
                lancamento.getTipo().name(),
                lancamento.getDescricao(),
                lancamento.getValor(),
                lancamento.getFormaPagamento(),
                lancamento.getDtHoraLancado(),
                usuarioExiste ? lancamento.getUser().getId() : null,
                usuarioExiste
                        ? lancamento.getUser().getNome()
                        : lancamento.getNomeUsuarioHistorico() + " (excluído)"
        );
    }
}
