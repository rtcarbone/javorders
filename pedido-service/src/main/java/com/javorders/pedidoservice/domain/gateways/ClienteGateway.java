package com.javorders.pedidoservice.domain.gateways;

import com.javorders.pedidoservice.domain.model.Cliente;

public interface ClienteGateway {
    Cliente buscarPorId(Long id);
}
