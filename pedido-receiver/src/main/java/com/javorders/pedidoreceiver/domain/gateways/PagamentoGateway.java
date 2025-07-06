package com.javorders.pedidoreceiver.domain.gateways;

import com.javorders.pedidoreceiver.domain.model.Pedido;

import java.util.UUID;

public interface PagamentoGateway {
    UUID criarOrdemPagamento(Pedido pedido);
}