package com.javorders.clienteservice.application.usecases;

import com.javorders.clienteservice.domain.model.Cliente;

import java.util.List;

public interface ConsultarClientesUsecase {
    List<Cliente> executar();
}
