package com.javorders.pedidoservice.infrastructure.gateway;

import com.javorders.pedidoservice.domain.gateways.ClienteGateway;
import com.javorders.pedidoservice.domain.model.Cliente;
import com.javorders.pedidoservice.infrastructure.dto.ClienteDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ClienteGatewayImpl implements ClienteGateway {

    private final WebClient webClient;

    public ClienteGatewayImpl(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://cliente-service:8080")
                .build();
    }

    @Override
    public Cliente buscarPorId(Long id) {
        return webClient.get()
                .uri("/clientes/" + id)
                .retrieve()
                .bodyToMono(ClienteDTO.class)
                .map(dto -> new Cliente(dto.id(), dto.nome(), dto.cpf()))
                .block();
    }

}
