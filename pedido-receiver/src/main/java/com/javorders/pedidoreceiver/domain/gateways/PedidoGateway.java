package com.javorders.pedidoreceiver.domain.gateways;

import com.javorders.pedidoreceiver.domain.model.Pedido;

public interface PedidoGateway {
    void salvar(Pedido pedido);
}