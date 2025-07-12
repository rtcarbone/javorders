package com.javorders.estoqueservice.infrastructure.persistence.mapper;

import com.javorders.estoqueservice.domain.model.Estoque;
import com.javorders.estoqueservice.infrastructure.persistence.entity.EstoqueEntity;

public class EstoqueMapper {

    public static EstoqueEntity toEntity(Estoque estoque) {
        return EstoqueEntity.builder()
            .id(estoque.getId())
            .sku(estoque.getSku())
            .quantidade(estoque.getQuantidade())
            .build();
    }

    public static Estoque toDomain(EstoqueEntity entity) {
        return Estoque.builder()
            .id(entity.getId())
            .sku(entity.getSku())
            .quantidade(entity.getQuantidade())
            .build();
    }
}