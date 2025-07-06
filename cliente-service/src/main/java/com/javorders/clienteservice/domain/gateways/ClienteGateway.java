package com.javorders.clienteservice.domain.gateways;

import com.javorders.clienteservice.domain.model.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteGateway {
    Cliente salvar(Cliente cliente);
    Optional<Cliente> buscarPorCpf(String cpf);
    List<Cliente> listarTodos();
}