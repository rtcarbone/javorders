package com.javorders.pedidoreceiver.infrastructure.gateways;

import com.javorders.pedidoreceiver.domain.gateways.PagamentoGateway;
import com.javorders.pedidoreceiver.domain.model.Pedido;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Component
public class PagamentoGatewayImpl implements PagamentoGateway {

    private final WebClient webClient;

    public PagamentoGatewayImpl(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://pagamento-service:8085").build();
    }

    @Override
    public UUID criarOrdemPagamento(Pedido pedido) {
        return webClient.post()
                .uri("/pagamentos")
                .bodyValue(pedido)
                .retrieve()
                .bodyToMono(UUID.class)
                .block();
    }
}