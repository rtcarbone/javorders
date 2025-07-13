package com.javorders.pedidoservice.infrastructure.dto;

import java.math.BigDecimal;

public record ProdutoDTO(
        String sku,
        BigDecimal preco
) {
}
