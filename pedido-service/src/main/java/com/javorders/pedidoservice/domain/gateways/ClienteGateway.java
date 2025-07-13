package com.javorders.pedidoservice.domain.gateways;

import com.javorders.pedidoservice.infrastructure.dto.ClienteDTO;

public interface ClienteGateway {
    ClienteDTO buscarPorId(Long id);
}
