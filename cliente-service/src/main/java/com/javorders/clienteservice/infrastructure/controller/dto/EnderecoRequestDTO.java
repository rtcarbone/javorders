package com.javorders.clienteservice.infrastructure.controller.dto;

public record EnderecoRequestDTO(
        String rua,
        String numero,
        String cep
) {
}
