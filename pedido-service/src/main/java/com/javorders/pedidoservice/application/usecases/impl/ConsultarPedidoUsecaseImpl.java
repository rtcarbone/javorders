package com.javorders.pedidoservice.application.usecases.impl;

import com.javorders.pedidoservice.application.usecases.ConsultarPedidoUsecase;
import com.javorders.pedidoservice.domain.gateways.PedidoGateway;
import com.javorders.pedidoservice.domain.model.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConsultarPedidoUsecaseImpl implements ConsultarPedidoUsecase {

    private final PedidoGateway pedidoGateway;

    @Override
    public Optional<Pedido> executar(Long id) {
        return pedidoGateway.buscarPorId(id);
    }

}
