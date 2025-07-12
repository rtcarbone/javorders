package com.javorders.clienteservice.application.usecases.impl;

import com.javorders.clienteservice.application.usecases.ConsultarClientesUsecase;
import com.javorders.clienteservice.domain.gateways.ClienteGateway;
import com.javorders.clienteservice.domain.model.Cliente;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultarClientesUsecaseImpl implements ConsultarClientesUsecase {

    private final ClienteGateway gateway;

    public ConsultarClientesUsecaseImpl(ClienteGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public List<Cliente> executar() {
        return gateway.listarTodos();
    }
}
