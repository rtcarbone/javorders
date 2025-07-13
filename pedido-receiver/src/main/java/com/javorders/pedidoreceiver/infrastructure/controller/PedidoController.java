package com.javorders.pedidoreceiver.infrastructure.controller;

import com.javorders.pedidoreceiver.infrastructure.controller.dto.PedidoRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos-receiver")
@RequiredArgsConstructor
public class PedidoController {

    private final RabbitTemplate rabbitTemplate;

    @PostMapping
    public ResponseEntity<Void> publicarPedido(@RequestBody PedidoRequestDTO dto) {
        rabbitTemplate.convertAndSend("novo-pedido-queue", dto);
        return ResponseEntity.accepted()
                .build();
    }

}
