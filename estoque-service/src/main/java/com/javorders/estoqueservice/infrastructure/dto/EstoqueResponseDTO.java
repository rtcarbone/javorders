package com.javorders.estoqueservice.infrastructure.dto;

import java.io.Serializable;

public record EstoqueResponseDTO(
        Long id,
        String sku,
        Integer quantidade
) implements Serializable {
}