package com.javorders.clienteservice.application.usecases.impl;

import com.javorders.clienteservice.application.usecases.AlterarClienteUsecase;
import com.javorders.clienteservice.domain.gateways.ClienteGateway;
import com.javorders.clienteservice.domain.model.Cliente;
import org.springframework.stereotype.Service;

@Service
public class AlterarClienteUsecaseImpl implements AlterarClienteUsecase {

    private final ClienteGateway gateway;

    public AlterarClienteUsecaseImpl(ClienteGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public Cliente executar(Long id, Cliente clienteAtualizado) {
        var existente = gateway.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado."));

        gateway.buscarPorCpf(clienteAtualizado.getCpf())
                .ifPresent(c -> {
                    if (!c.getId()
                            .equals(id)) {
                        throw new IllegalArgumentException("CPF já cadastrado por outro cliente.");
                    }
                });

        // Atualiza os dados do cliente existente
        var clienteAtualizadoComId = existente.toBuilder()
                .nome(clienteAtualizado.getNome())
                .cpf(clienteAtualizado.getCpf())
                .dataNascimento(clienteAtualizado.getDataNascimento())
                .enderecos(clienteAtualizado.getEnderecos())
                .build();

        return gateway.salvar(clienteAtualizadoComId);
    }
}
