package com.javorders.pedidoservice.infrastructure.dto;

import java.io.Serializable;

public record ClienteDTO(Long id,
                         String nome,
                         String cpf) implements Serializable {
}