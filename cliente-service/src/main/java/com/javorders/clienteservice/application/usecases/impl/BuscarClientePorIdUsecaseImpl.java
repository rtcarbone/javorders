package com.javorders.clienteservice.application.usecases.impl;

import com.javorders.clienteservice.application.usecases.BuscarClientePorIdUsecase;
import com.javorders.clienteservice.domain.gateways.ClienteGateway;
import com.javorders.clienteservice.domain.model.Cliente;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BuscarClientePorIdUsecaseImpl implements BuscarClientePorIdUsecase {

    private final ClienteGateway clienteGateway;

    public BuscarClientePorIdUsecaseImpl(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    @Override
    public Optional<Cliente> executar(Long id) {
        return clienteGateway.buscarPorId(id);
    }

}
