package com.javorders.clienteservice.infrastructure.dto;

import java.io.Serializable;

public record EnderecoRequestDTO(
        String rua,
        String numero,
        String cep
) implements Serializable {
}
