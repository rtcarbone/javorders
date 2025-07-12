package com.javorders.clienteservice.application.usecases;

import com.javorders.clienteservice.domain.model.Cliente;

public interface AlterarClienteUsecase {
    Cliente executar(Long id, Cliente cliente);
}
