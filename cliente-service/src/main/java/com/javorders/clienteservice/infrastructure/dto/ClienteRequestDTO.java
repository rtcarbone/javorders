package com.javorders.clienteservice.infrastructure.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public record ClienteRequestDTO(
        String nome,
        String cpf,
        LocalDate dataNascimento,
        List<EnderecoRequestDTO> enderecos
) implements Serializable {
}
