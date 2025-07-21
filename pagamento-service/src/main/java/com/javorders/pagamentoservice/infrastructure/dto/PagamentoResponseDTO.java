package com.javorders.pagamentoservice.infrastructure.dto;

import com.javorders.pagamentoservice.domain.model.StatusPagamento;

import java.io.Serializable;
import java.util.UUID;

public record PagamentoResponseDTO(UUID uuidTransacao,
                                   StatusPagamento status) implements Serializable {
}

