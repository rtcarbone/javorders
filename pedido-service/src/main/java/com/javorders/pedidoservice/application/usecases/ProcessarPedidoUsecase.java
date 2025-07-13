package com.javorders.pedidoservice.application.usecases;

import com.javorders.pedidoservice.domain.model.Pedido;

public interface ProcessarPedidoUsecase {
    void executar(Pedido pedido);
}
