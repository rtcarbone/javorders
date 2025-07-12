package com.javorders.produtoservice.infrastructure.persistence.mapper;

import com.javorders.produtoservice.domain.model.Produto;
import com.javorders.produtoservice.infrastructure.persistence.entity.ProdutoEntity;

public class ProdutoMapper {

    public static ProdutoEntity toEntity(Produto produto) {
        return ProdutoEntity.builder()
            .id(produto.getId())
            .nome(produto.getNome())
            .sku(produto.getSku())
            .preco(produto.getPreco())
            .build();
    }

    public static Produto toDomain(ProdutoEntity entity) {
        return Produto.builder()
            .id(entity.getId())
            .nome(entity.getNome())
            .sku(entity.getSku())
            .preco(entity.getPreco())
            .build();
    }
}