package com.javorders.clienteservice.application.usecases.impl;

import com.javorders.clienteservice.application.usecases.CadastrarClienteUsecase;
import com.javorders.clienteservice.domain.gateways.ClienteGateway;
import com.javorders.clienteservice.domain.model.Cliente;

public class CadastrarClienteUsecaseImpl implements CadastrarClienteUsecase {

    private final ClienteGateway clienteGateway;

    public CadastrarClienteUsecaseImpl(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    @Override
    public Cliente executar(Cliente cliente) {
        return clienteGateway.salvar(cliente);
    }
}