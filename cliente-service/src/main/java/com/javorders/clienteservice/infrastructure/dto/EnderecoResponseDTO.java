package com.javorders.clienteservice.infrastructure.dto;

import java.io.Serializable;

public record EnderecoResponseDTO(
        String rua,
        String numero,
        String cep
) implements Serializable {
}
