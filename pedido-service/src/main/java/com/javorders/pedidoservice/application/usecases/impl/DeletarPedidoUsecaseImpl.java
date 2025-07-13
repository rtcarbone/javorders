package com.javorders.pedidoservice.application.usecases.impl;

import com.javorders.pedidoservice.application.usecases.DeletarPedidoUsecase;
import com.javorders.pedidoservice.domain.gateways.PedidoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletarPedidoUsecaseImpl implements DeletarPedidoUsecase {

    private final PedidoGateway pedidoGateway;

    @Override
    public void executar(Long id) {
        pedidoGateway.deletarPorId(id);
    }

}