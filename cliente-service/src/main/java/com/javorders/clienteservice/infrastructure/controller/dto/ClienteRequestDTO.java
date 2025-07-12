package com.javorders.clienteservice.infrastructure.controller.dto;

import java.time.LocalDate;
import java.util.List;

public record ClienteRequestDTO(
        String nome,
        String cpf,
        LocalDate dataNascimento,
        List<EnderecoRequestDTO> enderecos
) {
}
