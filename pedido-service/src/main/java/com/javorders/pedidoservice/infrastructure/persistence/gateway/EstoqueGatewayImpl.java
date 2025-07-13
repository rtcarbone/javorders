package com.javorders.pedidoservice.infrastructure.persistence.gateway;

import com.javorders.pedidoservice.domain.gateways.EstoqueGateway;
import com.javorders.pedidoservice.domain.model.Pedido;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class EstoqueGatewayImpl implements EstoqueGateway {

    private final WebClient webClient;

    public EstoqueGatewayImpl(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://estoque-service:8083")
                .build();
    }

    @Override
    public void baixarEstoque(Pedido pedido) {
        webClient.patch()
                .uri("/estoques/baixar")
                .bodyValue(pedido.getItens())
                .retrieve()
                .toBodilessEntity()
                .block();
    }

}
