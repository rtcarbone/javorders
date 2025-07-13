package com.javorders.produtoservice.infrastructure.dto;

import java.math.BigDecimal;

public record ProdutoRequestDTO(
        String nome,
        String sku,
        BigDecimal preco
) {
}
