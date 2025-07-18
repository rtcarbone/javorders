package com.javorders.pedidoservice.domain.gateways;

import com.javorders.pedidoservice.domain.model.Pagamento;
import com.javorders.pedidoservice.domain.model.Pedido;

import java.util.Optional;
import java.util.UUID;

public interface PagamentoGateway {
    Pagamento solicitarPagamento(Pedido pedido);

    Optional<Pagamento> buscarPorUuid(UUID uuid);

    void estornar(Pedido pedido);
}
