package com.javorders.produtoservice.infrastructure.mapper;

import com.javorders.produtoservice.domain.model.Produto;
import com.javorders.produtoservice.infrastructure.dto.ProdutoResponseDTO;

public class ProdutoResponseMapper {

    public static ProdutoResponseDTO toResponse(Produto produto) {
        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getSku(),
                produto.getPreco()
        );
    }

}
