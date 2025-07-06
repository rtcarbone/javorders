package com.javorders.pedidoreceiver.application.usecases;

import com.javorders.pedidoreceiver.domain.model.Pedido;

public interface ProcessarPedidoUsecase {
    void processar(Pedido pedido);
}