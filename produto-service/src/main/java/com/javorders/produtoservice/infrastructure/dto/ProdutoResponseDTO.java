package com.javorders.produtoservice.infrastructure.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public record ProdutoResponseDTO(
        Long id,
        String nome,
        String sku,
        BigDecimal preco
) implements Serializable {
}
