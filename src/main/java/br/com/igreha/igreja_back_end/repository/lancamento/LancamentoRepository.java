package br.com.igreha.igreja_back_end.repository.lancamento;

import br.com.igreha.igreja_back_end.entity.lancamento.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento,Integer> {



}
