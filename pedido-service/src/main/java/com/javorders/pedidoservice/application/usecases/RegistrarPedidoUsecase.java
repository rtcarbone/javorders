package com.javorders.pedidoservice.application.usecases;

import com.javorders.pedidoservice.domain.model.Pedido;

public interface RegistrarPedidoUsecase {
    Pedido executar(Pedido pedido);
}