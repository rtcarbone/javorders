package com.javorders.clienteservice.application.usecases;

import com.javorders.clienteservice.domain.model.Cliente;

public interface CadastrarClienteUsecase {
    Cliente executar(Cliente cliente);
}