package com.javorders.produtoservice.infrastructure.controller.dto;

import java.math.BigDecimal;

public record ProdutoResponseDTO(
        Long id,
        String nome,
        String sku,
        BigDecimal preco
) {
}
