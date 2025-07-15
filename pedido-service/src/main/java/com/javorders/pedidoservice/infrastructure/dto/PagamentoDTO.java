package com.javorders.pedidoservice.infrastructure.dto;

public record PagamentoDTO(
        Long uuidTransacao,
        String status
) {
}
