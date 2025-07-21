package com.javorders.pagamentoservice.infrastructure.controller;

import com.javorders.pagamentoservice.application.usecases.BuscarPagamentoPorUuidUsecase;
import com.javorders.pagamentoservice.application.usecases.EstornarPagamentoUsecase;
import com.javorders.pagamentoservice.application.usecases.SolicitarPagamentoUsecase;
import com.javorders.pagamentoservice.domain.model.Pagamento;
import com.javorders.pagamentoservice.infrastructure.dto.PagamentoRequestDTO;
import com.javorders.pagamentoservice.infrastructure.dto.PagamentoResponseDTO;
import com.javorders.pagamentoservice.infrastructure.mapper.PagamentoDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/pagamentos")
@RequiredArgsConstructor
public class PagamentoController {

    private final SolicitarPagamentoUsecase solicitarPagamentoUsecase;
    private final BuscarPagamentoPorUuidUsecase buscarPagamentoPorUuidUsecase;
    private final EstornarPagamentoUsecase estornarPagamentoUsecase;

    @PostMapping
    public ResponseEntity<PagamentoResponseDTO> solicitarPagamento(@RequestBody PagamentoRequestDTO dto) {
        Pagamento pagamento = PagamentoDTOMapper.toDomain(dto);
        Pagamento solicitado = solicitarPagamentoUsecase.executar(pagamento);
        PagamentoResponseDTO response = PagamentoDTOMapper.toResponse(solicitado);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Pagamento> buscarPorUuid(@PathVariable UUID uuid) {
        Pagamento pagamento = buscarPagamentoPorUuidUsecase.executar(uuid);
        return ResponseEntity.ok(pagamento);
    }

    @PatchMapping("/{uuid}/estornar")
    public ResponseEntity<Void> estornar(@PathVariable UUID uuid) {
        estornarPagamentoUsecase.estornar(uuid);
        return ResponseEntity.noContent()
                .build();
    }

}