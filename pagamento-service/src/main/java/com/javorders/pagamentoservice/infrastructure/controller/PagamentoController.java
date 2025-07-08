package com.javorders.pagamentoservice.infrastructure.controller;

import com.javorders.pagamentoservice.application.usecases.EfetuarPagamentoUsecase;
import com.javorders.pagamentoservice.domain.model.Pagamento;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final EfetuarPagamentoUsecase usecase;

    public PagamentoController(EfetuarPagamentoUsecase usecase) {
        this.usecase = usecase;
    }

    @PostMapping
    public ResponseEntity<Pagamento> pagar(@RequestBody Pagamento pagamento) {
        return ResponseEntity.ok(usecase.executar(pagamento));
    }

}