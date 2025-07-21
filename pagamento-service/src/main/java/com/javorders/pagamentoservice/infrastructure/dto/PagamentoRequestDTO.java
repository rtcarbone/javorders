package com.javorders.pagamentoservice.infrastructure.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public record PagamentoRequestDTO(BigDecimal valor,
                                  String numeroCartao) implements Serializable {
}
