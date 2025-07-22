package com.javorders.pedidoservice.infrastructure.gateway;

import com.javorders.pedidoservice.domain.gateways.EstoqueGateway;
import com.javorders.pedidoservice.infrastructure.dto.EstoqueRequestDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class EstoqueGatewayImpl implements EstoqueGateway {

    private final WebClient webClient;

    public EstoqueGatewayImpl(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://estoque-service:8080")
                .build();
    }

    @Override
    public void baixarEstoque(String sku, Integer quantidade) {
        EstoqueRequestDTO dto = new EstoqueRequestDTO(sku, quantidade);

        webClient.patch()
                .uri("/estoques/baixar")
                .bodyValue(dto)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    @Override
    public void reporEstoque(String sku, Integer quantidade) {
        EstoqueRequestDTO dto = new EstoqueRequestDTO(sku, quantidade);

        webClient.patch()
                .uri("/estoques/restaurar")
                .bodyValue(dto)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

}
