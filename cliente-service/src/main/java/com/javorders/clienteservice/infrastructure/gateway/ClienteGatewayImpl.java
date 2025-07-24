package com.javorders.clienteservice.infrastructure.gateway;

import com.javorders.clienteservice.domain.gateways.ClienteGateway;
import com.javorders.clienteservice.domain.model.Cliente;
import com.javorders.clienteservice.infrastructure.persistence.mapper.ClienteMapper;
import com.javorders.clienteservice.infrastructure.persistence.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ClienteGatewayImpl implements ClienteGateway {

    private final ClienteRepository repository;

    @Override
    public Cliente salvar(Cliente cliente) {
        var entity = ClienteMapper.toEntity(cliente);
        var saved = repository.save(entity);
        return ClienteMapper.toDomain(saved);
    }

    @Override
    public Optional<Cliente> buscarPorCpf(String cpf) {
        return repository.findByCpf(cpf)
                .map(ClienteMapper::toDomain);
    }

    @Override
    public Optional<Cliente> buscarPorId(Long id) {
        return repository.findById(id)
                .map(ClienteMapper::toDomain);
    }

    @Override
    public List<Cliente> listarTodos() {
        return repository.findAll()
                .stream()
                .map(ClienteMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deletarPorId(Long id) {
        repository.deleteById(id);
    }
}