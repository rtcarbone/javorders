package com.javorders.pedidoservice.infrastructure.dto;

import java.io.Serializable;
import java.util.UUID;

public record PagamentoDTO(
        UUID uuidTransacao,
        String status
) implements Serializable {
}
