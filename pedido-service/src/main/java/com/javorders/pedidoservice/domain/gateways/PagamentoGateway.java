package com.javorders.pedidoservice.domain.gateways;

import com.javorders.pedidoservice.domain.model.Pedido;

import java.util.UUID;

public interface PagamentoGateway {
    UUID solicitarPagamento(Pedido pedido);
}
