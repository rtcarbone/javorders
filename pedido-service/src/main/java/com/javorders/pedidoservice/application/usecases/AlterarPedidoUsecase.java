package com.javorders.pedidoservice.application.usecases;

import com.javorders.pedidoservice.domain.model.Pedido;

public interface AlterarPedidoUsecase {
    Pedido executar(Long id, Pedido pedido);
}