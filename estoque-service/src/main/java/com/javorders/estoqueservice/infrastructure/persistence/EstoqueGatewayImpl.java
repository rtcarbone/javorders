package com.javorders.estoqueservice.infrastructure.persistence;

import com.javorders.estoqueservice.domain.gateways.EstoqueGateway;
import com.javorders.estoqueservice.domain.model.Estoque;
import com.javorders.estoqueservice.infrastructure.persistence.mapper.EstoqueMapper;
import com.javorders.estoqueservice.infrastructure.persistence.repository.EstoqueRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class EstoqueGatewayImpl implements EstoqueGateway {

    private final EstoqueRepository repository;

    public EstoqueGatewayImpl(EstoqueRepository repository) {
        this.repository = repository;
    }

    public Estoque salvar(Estoque estoque) {
        return EstoqueMapper.toDomain(repository.save(EstoqueMapper.toEntity(estoque)));
    }

    public Optional<Estoque> buscarPorSku(String sku) {
        return repository.findBySku(sku).map(EstoqueMapper::toDomain);
    }

    public List<Estoque> listarTodos() {
        return repository.findAll().stream().map(EstoqueMapper::toDomain).collect(Collectors.toList());
    }
}