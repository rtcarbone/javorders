package com.javorders.pedidoreceiver.infrastructure.gateways;

import com.javorders.pedidoreceiver.domain.gateways.EstoqueGateway;
import com.javorders.pedidoreceiver.domain.model.Pedido;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class EstoqueGatewayImpl implements EstoqueGateway {

    private final WebClient webClient;

    public EstoqueGatewayImpl(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://estoque-service:8083").build();
    }

    @Override
    public void baixaEstoque(Pedido pedido) {
        webClient.post()
                .uri("/estoques/baixa")
                .bodyValue(pedido)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}