package com.javorders.pagamentoservice.infrastructure.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public record PagamentoRequestDTO(
        Long clienteId,
        BigDecimal valor,
        String numeroCartao) implements Serializable {
}
