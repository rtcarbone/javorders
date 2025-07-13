package com.javorders.pedidoreceiver.infrastructure.gateways;

import com.javorders.pedidoreceiver.domain.gateways.PedidoGateway;
import com.javorders.pedidoreceiver.domain.model.Pedido;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class PedidoGatewayImpl implements PedidoGateway {

    private final WebClient webClient;

    public PedidoGatewayImpl(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://pedido-service:8084")
                .build();
    }

    @Override
    public void salvar(Pedido pedido) {
        webClient.post()
                .uri("/pedidos/registrar")
                .bodyValue(pedido)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

}