package com.javorders.pedidoservice.application.usecases;

import com.javorders.pedidoservice.domain.model.Pedido;

import java.util.Optional;

public interface ConsultarPedidoUsecase {
    Optional<Pedido> executar(Long id);
}
