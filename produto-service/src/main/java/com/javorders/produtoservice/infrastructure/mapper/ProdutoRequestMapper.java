package com.javorders.produtoservice.infrastructure.mapper;

import com.javorders.produtoservice.domain.model.Produto;
import com.javorders.produtoservice.infrastructure.dto.ProdutoRequestDTO;

public class ProdutoRequestMapper {

    public static Produto toDomain(ProdutoRequestDTO dto) {
        return Produto.builder()
                .nome(dto.nome())
                .sku(dto.sku())
                .preco(dto.preco())
                .build();
    }

}