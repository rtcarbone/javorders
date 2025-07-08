package com.javorders.pedidoreceiver.infrastructure.messaging;

import com.javorders.pedidoreceiver.application.usecases.ProcessarPedidoUsecase;
import com.javorders.pedidoreceiver.domain.model.Pedido;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NovoPedidoEntryPoint {

    private final ProcessarPedidoUsecase processarPedidoUsecase;

    public NovoPedidoEntryPoint(ProcessarPedidoUsecase processarPedidoUsecase) {
        this.processarPedidoUsecase = processarPedidoUsecase;
    }

    @RabbitListener(queues = "novo-pedido-queue")
    public void listen(Pedido pedido) {
        processarPedidoUsecase.processar(pedido);
    }

}