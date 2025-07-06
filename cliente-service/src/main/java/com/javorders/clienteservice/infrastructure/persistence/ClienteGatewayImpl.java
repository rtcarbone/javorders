package com.javorders.clienteservice.infrastructure.persistence;

import com.javorders.clienteservice.domain.gateways.ClienteGateway;
import com.javorders.clienteservice.domain.model.Cliente;
import com.javorders.clienteservice.infrastructure.persistence.mapper.ClienteMapper;
import com.javorders.clienteservice.infrastructure.persistence.repository.ClienteRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ClienteGatewayImpl implements ClienteGateway {

    private final ClienteRepository clienteRepository;

    public ClienteGatewayImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Cliente salvar(Cliente cliente) {
        var entity = ClienteMapper.toEntity(cliente);
        return ClienteMapper.toDomain(clienteRepository.save(entity));
    }

    @Override
    public Optional<Cliente> buscarPorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf).map(ClienteMapper::toDomain);
    }

    @Override
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll().stream().map(ClienteMapper::toDomain).collect(Collectors.toList());
    }
}