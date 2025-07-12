package com.javorders.clienteservice.application.usecases.impl;

import com.javorders.clienteservice.application.usecases.DeletarClienteUsecase;
import com.javorders.clienteservice.domain.gateways.ClienteGateway;
import org.springframework.stereotype.Service;

@Service
public class DeletarClienteUsecaseImpl implements DeletarClienteUsecase {

    private final ClienteGateway gateway;

    public DeletarClienteUsecaseImpl(ClienteGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public void executar(Long id) {
        gateway.deletarPorId(id);
    }
}
