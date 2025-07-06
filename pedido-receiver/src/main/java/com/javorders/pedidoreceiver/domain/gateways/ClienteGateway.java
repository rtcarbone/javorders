package com.javorders.pedidoreceiver.domain.gateways;

public interface ClienteGateway {
    ClienteDTO getById(Long id);

    class ClienteDTO {
        public Long id;
        public String nome;
    }
}