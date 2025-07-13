package com.javorders.pedidoservice.domain.gateways;

import com.javorders.pedidoservice.domain.model.Pedido;

public interface EstoqueGateway {
    void baixarEstoque(Pedido pedido);
}
