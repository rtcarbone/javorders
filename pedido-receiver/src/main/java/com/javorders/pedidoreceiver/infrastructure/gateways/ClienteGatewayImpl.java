package com.javorders.pedidoreceiver.infrastructure.gateways;

import com.javorders.pedidoreceiver.domain.gateways.ClienteGateway;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ClienteGatewayImpl implements ClienteGateway {

    private final WebClient webClient;

    public ClienteGatewayImpl(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://cliente-service:8081").build();
    }

    @Override
    public ClienteDTO getById(Long id) {
        return webClient.get()
                .uri("/clientes/" + id)
                .retrieve()
                .bodyToMono(ClienteDTO.class)
                .block();
    }
}