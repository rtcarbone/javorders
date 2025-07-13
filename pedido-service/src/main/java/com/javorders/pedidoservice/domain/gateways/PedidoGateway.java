package com.javorders.pedidoservice.domain.gateways;

import com.javorders.pedidoservice.domain.model.Pedido;

import java.util.Optional;

public interface PedidoGateway {

    Pedido salvar(Pedido pedido);

    Optional<Pedido> buscarPorId(Long id);

    void deletarPorId(Long id);

}