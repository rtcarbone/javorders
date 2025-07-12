package com.javorders.estoqueservice.infrastructure.controller.dto;

public record EstoqueResponseDTO(
        Long id,
        String sku,
        Integer quantidade
) {
}