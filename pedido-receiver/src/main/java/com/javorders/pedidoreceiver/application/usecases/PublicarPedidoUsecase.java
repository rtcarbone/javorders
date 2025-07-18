package com.javorders.pedidoreceiver.application.usecases;

import com.javorders.pedidoreceiver.domain.model.Pedido;

public interface PublicarPedidoUsecase {
    void executar(Pedido pedido);
}
