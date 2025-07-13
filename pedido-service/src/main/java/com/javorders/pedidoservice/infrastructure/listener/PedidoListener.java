package com.javorders.pedidoservice.infrastructure.listener;

import com.javorders.pedidoservice.application.usecases.ProcessarPedidoUsecase;
import com.javorders.pedidoservice.domain.model.Pedido;
import com.javorders.pedidoservice.infrastructure.dto.PedidoRequestDTO;
import com.javorders.pedidoservice.infrastructure.mapper.PedidoDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PedidoListener {

    private final ProcessarPedidoUsecase usecase;

    @RabbitListener(queues = "novo-pedido-queue")
    public void receberPedido(PedidoRequestDTO dto) {
        Pedido pedido = PedidoDTOMapper.toDomain(dto);
        usecase.executar(pedido);
    }

}
