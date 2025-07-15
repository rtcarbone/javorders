package com.javorders.pedidoservice.domain.gateways;

import com.javorders.pedidoservice.domain.model.Pedido;
import com.javorders.pedidoservice.infrastructure.dto.PagamentoDTO;

import java.util.UUID;

public interface PagamentoGateway {
    PagamentoDTO solicitarPagamento(Pedido pedido);
    void estornar(Pedido pedido);
}
