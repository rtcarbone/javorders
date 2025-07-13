package com.javorders.clienteservice.infrastructure.mapper;

import com.javorders.clienteservice.domain.model.Cliente;
import com.javorders.clienteservice.domain.model.Endereco;
import com.javorders.clienteservice.infrastructure.dto.ClienteRequestDTO;
import com.javorders.clienteservice.infrastructure.dto.EnderecoRequestDTO;

import java.util.List;

public class ClienteRequestMapper {

    public static Cliente toDomain(ClienteRequestDTO dto) {
        List<Endereco> enderecos = dto.enderecos()
                .stream()
                .map(ClienteRequestMapper::toDomain)
                .toList();

        return Cliente.builder()
                .nome(dto.nome())
                .cpf(dto.cpf())
                .dataNascimento(dto.dataNascimento())
                .enderecos(enderecos)
                .build();
    }

    private static Endereco toDomain(EnderecoRequestDTO dto) {
        return Endereco.builder()
                .rua(dto.rua())
                .numero(dto.numero())
                .cep(dto.cep())
                .build();
    }

}
