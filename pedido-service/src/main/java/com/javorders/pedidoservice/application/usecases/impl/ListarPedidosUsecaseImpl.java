package com.javorders.pedidoservice.application.usecases.impl;

import com.javorders.pedidoservice.application.usecases.ListarPedidosUsecase;
import com.javorders.pedidoservice.domain.gateways.PedidoGateway;
import com.javorders.pedidoservice.domain.model.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarPedidosUsecaseImpl implements ListarPedidosUsecase {

    private final PedidoGateway pedidoGateway;

    @Override
    public List<Pedido> executar() {
        return pedidoGateway.buscarTodos();
    }

}
