package com.javorders.clienteservice.infrastructure.controller.dto;

public record EnderecoResponseDTO(
        String rua,
        String numero,
        String cep
) {
}
