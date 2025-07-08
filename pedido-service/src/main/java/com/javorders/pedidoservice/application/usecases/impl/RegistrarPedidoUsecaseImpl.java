package com.javorders.pedidoservice.application.usecases.impl;

import com.javorders.pedidoservice.application.usecases.RegistrarPedidoUsecase;
import com.javorders.pedidoservice.domain.gateways.PedidoGateway;
import com.javorders.pedidoservice.domain.model.Pedido;
import com.javorders.pedidoservice.domain.model.StatusPedido;
import org.springframework.stereotype.Service;

@Service
public class RegistrarPedidoUsecaseImpl implements RegistrarPedidoUsecase {

    private final PedidoGateway pedidoGateway;

    public RegistrarPedidoUsecaseImpl(PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    public Pedido executar(Pedido pedido) {
        pedido.setStatus(StatusPedido.ABERTO);
        return pedidoGateway.salvar(pedido);
    }

}