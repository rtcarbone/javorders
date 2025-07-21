package com.javorders.clienteservice.infrastructure.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public record ClienteResponseDTO(
        Long id,
        String nome,
        String cpf,
        LocalDate dataNascimento,
        List<EnderecoResponseDTO> enderecos
) implements Serializable {
}
