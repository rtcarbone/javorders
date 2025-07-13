package com.javorders.pedidoservice.application.usecases;

import com.javorders.pedidoservice.domain.model.Pedido;

import java.util.List;

public interface ListarPedidosUsecase {
    List<Pedido> executar();
}
