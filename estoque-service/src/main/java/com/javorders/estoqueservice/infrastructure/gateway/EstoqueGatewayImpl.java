package com.javorders.estoqueservice.infrastructure.gateway;

import com.javorders.estoqueservice.domain.gateways.EstoqueGateway;
import com.javorders.estoqueservice.domain.model.Estoque;
import com.javorders.estoqueservice.infrastructure.persistence.mapper.EstoqueMapper;
import com.javorders.estoqueservice.infrastructure.persistence.repository.EstoqueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EstoqueGatewayImpl implements EstoqueGateway {

    private final EstoqueRepository repository;

    @Override
    public Estoque salvar(Estoque estoque) {
        var entity = EstoqueMapper.toEntity(estoque);
        var salvo = repository.save(entity);
        return EstoqueMapper.toDomain(salvo);
    }

    @Override
    public Optional<Estoque> buscarPorSku(String sku) {
        return repository.findBySku(sku)
                .map(EstoqueMapper::toDomain);
    }

    @Override
    public void baixarEstoque(String sku, Integer quantidade) {
        repository.findBySku(sku)
                .ifPresent(entity -> {
                    entity.setQuantidade(entity.getQuantidade() - quantidade);
                    repository.save(entity);
                });
    }

    @Override
    public void restaurarEstoque(String sku, Integer quantidade) {
        repository.findBySku(sku)
                .ifPresent(entity -> {
                    entity.setQuantidade(entity.getQuantidade() + quantidade);
                    repository.save(entity);
                });
    }

}