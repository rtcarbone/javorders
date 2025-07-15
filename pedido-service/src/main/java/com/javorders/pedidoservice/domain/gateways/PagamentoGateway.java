package com.javorders.pedidoservice.domain.gateways;

import com.javorders.pedidoservice.domain.model.Pagamento;
import com.javorders.pedidoservice.domain.model.Pedido;

public interface PagamentoGateway {
    Pagamento solicitarPagamento(Pedido pedido);

    void estornar(Pedido pedido);
}
