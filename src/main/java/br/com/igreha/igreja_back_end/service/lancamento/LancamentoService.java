package br.com.igreha.igreja_back_end.service.lancamento;

import br.com.igreha.igreja_back_end.dto.lancamento.*;
import br.com.igreha.igreja_back_end.entity.lancamento.Lancamento;
import br.com.igreha.igreja_back_end.entity.user.User;
import br.com.igreha.igreja_back_end.enums.lancamento.Tipo;
import br.com.igreha.igreja_back_end.exception.AccessDeniedCustomException;
import br.com.igreha.igreja_back_end.exception.LancamentoNotFoundException;
import br.com.igreha.igreja_back_end.repository.lancamento.LancamentoRepository;
import br.com.igreha.igreja_back_end.specification.LancamentoSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
public class LancamentoService {

    private final LancamentoRepository lancamentoRepository;
    private final br.com.igreha.igreja_back_end.mapper.lancamento.LancamentoMapper lancamentoMapper;

    public LancamentoResponseDTO create(LancamentoRequestDTO request, User usuarioLogado) {
        Lancamento lancamento = new Lancamento();
        lancamento.setTipo(request.getTipo());
        lancamento.setDescricao(request.getDescricao());
        lancamento.setValor(request.getValor());
        lancamento.setFormaPagamento(request.getFormaPagamento());
        lancamento.setUser(usuarioLogado); // dono vem do token, nunca do request

        Lancamento salvo = lancamentoRepository.save(lancamento);
        return lancamentoMapper.toResponseDTO(salvo);
    }

    public List<LancamentoResponseDTO> listAll() {
        return lancamentoRepository.findAll()
                .stream()
                .map(lancamentoMapper::toResponseDTO)
                .toList();
    }

    public LancamentoResponseDTO findById(Integer id) {
        Lancamento lancamento = lancamentoRepository.findById(id)
                .orElseThrow(() -> new LancamentoNotFoundException("Lançamento não encontrado"));
        return lancamentoMapper.toResponseDTO(lancamento);
    }

    public LancamentoResponseDTO update(Integer id, LancamentoUpdateRequestDTO request, User usuarioLogado) {
        Lancamento lancamento = lancamentoRepository.findById(id)
                .orElseThrow(() -> new LancamentoNotFoundException("Lançamento não encontrado"));

        validarDonoOuPastor(lancamento, usuarioLogado);

        lancamento.setTipo(request.getTipo());
        lancamento.setDescricao(request.getDescricao());
        lancamento.setValor(request.getValor());
        lancamento.setFormaPagamento(request.getFormaPagamento());

        Lancamento atualizado = lancamentoRepository.save(lancamento);
        return lancamentoMapper.toResponseDTO(atualizado);
    }

    public void delete(Integer id, User usuarioLogado) {
        Lancamento lancamento = lancamentoRepository.findById(id)
                .orElseThrow(() -> new LancamentoNotFoundException("Lançamento não encontrado"));

        validarDonoOuPastor(lancamento, usuarioLogado);

        lancamentoRepository.delete(lancamento);
    }

    private void validarDonoOuPastor(Lancamento lancamento, User usuarioLogado) {
        boolean isDono = lancamento.getUser().getId().equals(usuarioLogado.getId());
        boolean isPastor = usuarioLogado.getRole().name().equals("PASTOR");

        if (!isDono && !isPastor) {
            throw new AccessDeniedCustomException("Você só pode editar/excluir seus próprios lançamentos");
        }
    }
    public DashboardDTO getDashboard() {
        List<Lancamento> todos = lancamentoRepository.findAll();

        // KPIs
        BigDecimal totalEntradas = todos.stream()
                .filter(l -> l.getTipo() == Tipo.ENTRADA)
                .map(Lancamento::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalSaidas = todos.stream()
                .filter(l -> l.getTipo() == Tipo.SAIDA)
                .map(Lancamento::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal saldoFinal = totalEntradas.subtract(totalSaidas);

        ResumoFinanceiroDTO resumo = new ResumoFinanceiroDTO(totalEntradas, totalSaidas, saldoFinal);

        // Evolução mensal
        Map<String, BigDecimal[]> porMes = new TreeMap<>();
        for (Lancamento l : todos) {
            String chave = l.getDtHoraLancado().getYear() + "-" +
                    String.format("%02d", l.getDtHoraLancado().getMonthValue());

            porMes.putIfAbsent(chave, new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO});

            if (l.getTipo() == Tipo.ENTRADA) {
                porMes.get(chave)[0] = porMes.get(chave)[0].add(l.getValor());
            } else {
                porMes.get(chave)[1] = porMes.get(chave)[1].add(l.getValor());
            }
        }

        List<EvolucaoMensalDTO> evolucaoMensal = porMes.entrySet().stream()
                .map(e -> new EvolucaoMensalDTO(e.getKey(), e.getValue()[0], e.getValue()[1]))
                .toList();

        // Distribuição por forma de pagamento
        Map<String, BigDecimal> porFormaPagamento = new HashMap<>();
        for (Lancamento l : todos) {
            String forma = l.getFormaPagamento() != null ? l.getFormaPagamento() : "Não informado";
            porFormaPagamento.merge(forma, l.getValor(), BigDecimal::add);
        }

        List<FormaPagamentoDTO> distribuicao = porFormaPagamento.entrySet().stream()
                .map(e -> new FormaPagamentoDTO(e.getKey(), e.getValue()))
                .toList();

        return new DashboardDTO(resumo, evolucaoMensal, distribuicao);
    }
    public Page<LancamentoResponseDTO> listPaginado(Tipo tipo, LocalDateTime dataInicio, LocalDateTime dataFim, Pageable pageable) {
        Specification<Lancamento> spec = LancamentoSpecification.comFiltros(tipo, dataInicio, dataFim);

        return lancamentoRepository.findAll(spec, pageable)
                .map(lancamentoMapper::toResponseDTO);
    }
}