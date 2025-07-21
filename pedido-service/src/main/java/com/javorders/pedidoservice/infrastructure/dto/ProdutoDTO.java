package com.javorders.pedidoservice.infrastructure.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public record ProdutoDTO(
        String sku,
        BigDecimal preco
) implements Serializable {
}
