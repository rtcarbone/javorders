package com.javorders.pagamentoservice.infrastructure.dto;

import java.math.BigDecimal;

public record PagamentoRequestDTO(BigDecimal valor,
                                  String numeroCartao) {
}
