package com.javorders.pedidoservice.infrastructure.controller;

import com.javorders.pedidoservice.application.usecases.ProcessarNotificacaoPagamentoUsecase;
import com.javorders.pedidoservice.infrastructure.dto.NotificacaoPagamentoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pagamentos")
public class NotificacaoPagamentoController {

    private final ProcessarNotificacaoPagamentoUsecase usecase;

    @PostMapping("/notificacao")
    public ResponseEntity<Void> receberNotificacao(@RequestBody NotificacaoPagamentoRequest request) {
        usecase.executar(request.uuidTransacao());
        return ResponseEntity.ok()
                .build();
    }
}
