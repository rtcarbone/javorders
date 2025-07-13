package com.javorders.pedidoservice.infrastructure.dto;

import java.math.BigDecimal;

public record PagamentoDTO(
        Long pedidoId,
        BigDecimal valor,
        String status
) {
}
