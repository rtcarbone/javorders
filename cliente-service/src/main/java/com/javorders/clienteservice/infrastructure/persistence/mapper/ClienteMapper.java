package com.javorders.clienteservice.infrastructure.persistence.mapper;

import com.javorders.clienteservice.domain.model.Cliente;
import com.javorders.clienteservice.domain.model.Endereco;
import com.javorders.clienteservice.infrastructure.persistence.entity.ClienteEntity;
import com.javorders.clienteservice.infrastructure.persistence.entity.EnderecoEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ClienteMapper {

    public static ClienteEntity toEntity(Cliente cliente) {
        var entity = ClienteEntity.builder()
            .id(cliente.getId())
            .nome(cliente.getNome())
            .cpf(cliente.getCpf())
            .dataNascimento(cliente.getDataNascimento())
            .build();

        List<EnderecoEntity> enderecos = cliente.getEnderecos().stream().map(e ->
            EnderecoEntity.builder()
                .rua(e.getRua())
                .numero(e.getNumero())
                .cep(e.getCep())
                .cliente(entity)
                .build()
        ).collect(Collectors.toList());

        entity.setEnderecos(enderecos);
        return entity;
    }

    public static Cliente toDomain(ClienteEntity entity) {
        return Cliente.builder()
            .id(entity.getId())
            .nome(entity.getNome())
            .cpf(entity.getCpf())
            .dataNascimento(entity.getDataNascimento())
            .enderecos(entity.getEnderecos().stream().map(e ->
                Endereco.builder()
                    .rua(e.getRua())
                    .numero(e.getNumero())
                    .cep(e.getCep())
                    .build()
            ).collect(Collectors.toList()))
            .build();
    }
}