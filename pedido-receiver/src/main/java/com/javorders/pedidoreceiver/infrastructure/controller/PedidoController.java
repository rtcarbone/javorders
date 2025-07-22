package com.javorders.pedidoreceiver.infrastructure.controller;

import com.javorders.pedidoreceiver.application.usecases.PublicarPedidoUsecase;
import com.javorders.pedidoreceiver.infrastructure.dto.PedidoDTO;
import com.javorders.pedidoreceiver.infrastructure.mapper.PedidoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/pedidos-receiver")
@RequiredArgsConstructor
public class PedidoController {

    private final PublicarPedidoUsecase publicarPedidoUsecase;
    private final PedidoMapper pedidoMapper;

    @PostMapping
    public ResponseEntity<Void> publicarPedido(@RequestBody PedidoDTO dto) {
        var pedido = pedidoMapper.toDomain(dto);
        publicarPedidoUsecase.executar(pedido);
        return ResponseEntity.accepted()
                .build();
    }

}
