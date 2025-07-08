package com.javorders.pedidoservice.infrastructure.controller;

import com.javorders.pedidoservice.application.usecases.RegistrarPedidoUsecase;
import com.javorders.pedidoservice.domain.model.Pedido;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final RegistrarPedidoUsecase usecase;

    public PedidoController(RegistrarPedidoUsecase usecase) {
        this.usecase = usecase;
    }

    @PostMapping
    public ResponseEntity<Pedido> criar(@RequestBody Pedido pedido) {
        return ResponseEntity.ok(usecase.executar(pedido));
    }

}