package com.javorders.pedidoservice.infrastructure.dto;

import java.util.UUID;

public record PagamentoDTO(
        UUID uuidTransacao,
        String status
) {
}
