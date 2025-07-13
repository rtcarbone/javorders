package com.javorders.clienteservice.infrastructure.dto;

public record EnderecoRequestDTO(
        String rua,
        String numero,
        String cep
) {
}
