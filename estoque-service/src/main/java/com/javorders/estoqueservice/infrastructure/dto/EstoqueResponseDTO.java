package com.javorders.estoqueservice.infrastructure.dto;

public record EstoqueResponseDTO(
        Long id,
        String sku,
        Integer quantidade
) {
}