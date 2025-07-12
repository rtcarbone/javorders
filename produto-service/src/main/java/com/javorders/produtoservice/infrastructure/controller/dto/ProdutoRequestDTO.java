package com.javorders.produtoservice.infrastructure.controller.dto;

import java.math.BigDecimal;

public record ProdutoRequestDTO(
        String nome,
        String sku,
        BigDecimal preco
) {
}
