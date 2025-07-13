package com.javorders.pagamentoservice.infrastructure.controller;

import com.javorders.pagamentoservice.application.usecases.EfetuarPagamentoUsecase;
import com.javorders.pagamentoservice.application.usecases.EstornarPagamentoUsecase;
import com.javorders.pagamentoservice.domain.model.Pagamento;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/pagamentos")
@RequiredArgsConstructor
public class PagamentoController {

    private final EfetuarPagamentoUsecase efetuarPagamentoUsecase;
    private final EstornarPagamentoUsecase estornarPagamentoUsecase;

    @PostMapping
    public ResponseEntity<Pagamento> pagar(@RequestBody Pagamento pagamento) {
        return ResponseEntity.ok(efetuarPagamentoUsecase.executar(pagamento));
    }

    @PatchMapping("/{uuid}/estornar")
    public ResponseEntity<Void> estornar(@PathVariable UUID uuid) {
        estornarPagamentoUsecase.estornar(uuid);
        return ResponseEntity.noContent()
                .build();
    }

}