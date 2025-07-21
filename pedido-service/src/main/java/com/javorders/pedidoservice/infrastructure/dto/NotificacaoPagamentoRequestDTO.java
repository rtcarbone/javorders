package com.javorders.pedidoservice.infrastructure.dto;

import java.io.Serializable;
import java.util.UUID;

public record NotificacaoPagamentoRequestDTO(UUID uuidTransacao) implements Serializable {
}
