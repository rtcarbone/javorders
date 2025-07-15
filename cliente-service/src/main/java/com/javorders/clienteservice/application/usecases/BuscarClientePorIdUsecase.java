package com.javorders.clienteservice.application.usecases;

import com.javorders.clienteservice.domain.model.Cliente;

import java.util.Optional;

public interface BuscarClientePorIdUsecase {
    Optional<Cliente> executar(Long id);
}
