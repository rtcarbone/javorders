package com.javorders.estoqueservice.infrastructure.dto;

import java.io.Serializable;

public record EstoqueRequestDTO(
        String sku,
        Integer quantidade
) implements Serializable {
}
