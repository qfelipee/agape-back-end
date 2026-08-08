package br.com.igreha.igreja_back_end.specification;

import br.com.igreha.igreja_back_end.entity.lancamento.Lancamento;
import br.com.igreha.igreja_back_end.enums.lancamento.Tipo;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class LancamentoSpecification {

    public static Specification<Lancamento> comFiltros(Tipo tipo, LocalDateTime dataInicio, LocalDateTime dataFim) {
        return (root, query, cb) -> {
            var predicates = cb.conjunction();

            if (tipo != null) {
                predicates = cb.and(predicates, cb.equal(root.get("tipo"), tipo));
            }
            if (dataInicio != null) {
                predicates = cb.and(predicates, cb.greaterThanOrEqualTo(root.get("dtHoraLancado"), dataInicio));
            }
            if (dataFim != null) {
                predicates = cb.and(predicates, cb.lessThanOrEqualTo(root.get("dtHoraLancado"), dataFim));
            }

            return predicates;
        };
    }
}