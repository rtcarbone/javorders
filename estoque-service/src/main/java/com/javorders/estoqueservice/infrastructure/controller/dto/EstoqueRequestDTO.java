package com.javorders.estoqueservice.infrastructure.controller.dto;

public record EstoqueRequestDTO(
        String sku,
        Integer quantidade
) {
}
