package com.javorders.clienteservice.domain.model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    private Long id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private List<Endereco> enderecos;
}