package com.javorders.pedidoservice.application.usecases.impl;

import com.javorders.pedidoservice.application.usecases.AlterarPedidoUsecase;
import com.javorders.pedidoservice.domain.gateways.PedidoGateway;
import com.javorders.pedidoservice.domain.model.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlterarPedidoUsecaseImpl implements AlterarPedidoUsecase {

    private final PedidoGateway pedidoGateway;

    @Override
    public Pedido executar(Long id, Pedido pedido) {
        var pedidoAtualizado = pedido.toBuilder()
                .id(id)
                .build();

        return pedidoGateway.salvar(pedidoAtualizado);
    }

}
