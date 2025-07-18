package com.javorders.pedidoreceiver.application.usecases.impl;

import com.javorders.pedidoreceiver.application.usecases.PublicarPedidoUsecase;
import com.javorders.pedidoreceiver.domain.gateways.PedidoPublisherGateway;
import com.javorders.pedidoreceiver.domain.model.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublicarPedidoUsecaseImpl implements PublicarPedidoUsecase {

    private final PedidoPublisherGateway pedidoPublisherGateway;

    @Override
    public void executar(Pedido pedido) {
        pedidoPublisherGateway.publicar(pedido);
    }

}