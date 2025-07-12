package com.javorders.clienteservice.application.usecases.impl;

import com.javorders.clienteservice.application.usecases.CadastrarClienteUsecase;
import com.javorders.clienteservice.domain.gateways.ClienteGateway;
import com.javorders.clienteservice.domain.model.Cliente;
import org.springframework.stereotype.Service;

@Service
public class CadastrarClienteUsecaseImpl implements CadastrarClienteUsecase {

    private final ClienteGateway gateway;

    public CadastrarClienteUsecaseImpl(ClienteGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public Cliente executar(Cliente cliente) {
        boolean cpfJaExiste = gateway.buscarPorCpf(cliente.getCpf())
                .isPresent();
        if (cpfJaExiste) {
            throw new IllegalArgumentException("CPF já cadastrado.");
        }
        return gateway.salvar(cliente);
    }
}
