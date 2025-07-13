package com.javorders.clienteservice.infrastructure.mapper;

import com.javorders.clienteservice.domain.model.Cliente;
import com.javorders.clienteservice.domain.model.Endereco;
import com.javorders.clienteservice.infrastructure.dto.ClienteResponseDTO;
import com.javorders.clienteservice.infrastructure.dto.EnderecoResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ClienteResponseMapper {

    public static ClienteResponseDTO toResponse(Cliente cliente) {
        List<EnderecoResponseDTO> enderecos = cliente.getEnderecos()
                .stream()
                .map(ClienteResponseMapper::toResponse)
                .collect(Collectors.toList());

        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getDataNascimento(),
                enderecos
        );
    }

    private static EnderecoResponseDTO toResponse(Endereco endereco) {
        return new EnderecoResponseDTO(
                endereco.getRua(),
                endereco.getNumero(),
                endereco.getCep()
        );
    }

}
