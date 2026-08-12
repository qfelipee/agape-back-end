package br.com.igreha.igreja_back_end.repository.lancamento;

import br.com.igreha.igreja_back_end.entity.lancamento.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface LancamentoRepository extends JpaRepository<Lancamento, Integer>,
        JpaSpecificationExecutor<Lancamento> {
    List<Lancamento> findByUser_Id(Integer userId);
}

