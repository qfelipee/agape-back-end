package br.com.igreha.igreja_back_end.controller;

import br.com.igreha.igreja_back_end.dto.lancamento.DashboardDTO;
import br.com.igreha.igreja_back_end.dto.lancamento.LancamentoRequestDTO;
import br.com.igreha.igreja_back_end.dto.lancamento.LancamentoResponseDTO;
import br.com.igreha.igreja_back_end.dto.lancamento.LancamentoUpdateRequestDTO;
import br.com.igreha.igreja_back_end.entity.user.User;
import br.com.igreha.igreja_back_end.enums.lancamento.Tipo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/lancamentos")
@RequiredArgsConstructor
public class LancamentoController {

    private final br.com.igreha.igreja_back_end.service.lancamento.LancamentoService lancamentoService;

    @PostMapping
    public ResponseEntity<LancamentoResponseDTO> create(
            @RequestBody @Valid LancamentoRequestDTO request,
            @AuthenticationPrincipal User usuarioLogado
    ) {
        LancamentoResponseDTO response = lancamentoService.create(request, usuarioLogado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<LancamentoResponseDTO>> listAll() {
        return ResponseEntity.ok(lancamentoService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LancamentoResponseDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(lancamentoService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LancamentoResponseDTO> update(
            @PathVariable Integer id,
            @RequestBody @Valid LancamentoUpdateRequestDTO request,
            @AuthenticationPrincipal User usuarioLogado
    ) {
        LancamentoResponseDTO response = lancamentoService.update(id, request, usuarioLogado);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id,
            @AuthenticationPrincipal User usuarioLogado
    ) {
        lancamentoService.delete(id, usuarioLogado);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardDTO> getDashboard() {
        return ResponseEntity.ok(lancamentoService.getDashboard());
    }

    @GetMapping
    public ResponseEntity<Page<LancamentoResponseDTO>> listAll(
            @RequestParam(required = false) Tipo tipo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim,
            @PageableDefault(size = 20, sort = "dtHoraLancado", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(lancamentoService.listPaginado(tipo, dataInicio, dataFim, pageable));
    }


}