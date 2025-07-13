package com.javorders.estoqueservice.infrastructure.dto;

public record EstoqueRequestDTO(
        String sku,
        Integer quantidade
) {
}
