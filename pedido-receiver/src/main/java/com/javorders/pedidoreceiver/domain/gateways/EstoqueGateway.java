package com.javorders.pedidoreceiver.domain.gateways;

import com.javorders.pedidoreceiver.domain.model.Pedido;

public interface EstoqueGateway {
    void baixaEstoque(Pedido pedido);
}