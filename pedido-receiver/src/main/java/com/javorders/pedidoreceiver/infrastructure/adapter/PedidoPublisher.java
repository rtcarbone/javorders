package com.javorders.pedidoreceiver.infrastructure.adapter;

import com.javorders.pedidoreceiver.domain.gateways.PedidoPublisherGateway;
import com.javorders.pedidoreceiver.domain.model.Pedido;
import com.javorders.pedidoreceiver.infrastructure.mapper.PedidoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PedidoPublisher implements PedidoPublisherGateway {

    private final RabbitTemplate rabbitTemplate;
    private final PedidoMapper mapper;

    @Override
    public void publicar(Pedido pedido) {
        var mensagem = mapper.toDTO(pedido);
        rabbitTemplate.convertAndSend("novo-pedido-queue", mensagem);
    }

}