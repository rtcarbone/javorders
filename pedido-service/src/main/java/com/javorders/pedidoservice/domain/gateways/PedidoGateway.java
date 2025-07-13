package com.javorders.pedidoservice.domain.gateways;

import com.javorders.pedidoservice.domain.model.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidoGateway {

    Pedido salvar(Pedido pedido);

    Optional<Pedido> buscarPorId(Long id);

    List<Pedido> buscarTodos();

    void deletarPorId(Long id);

}