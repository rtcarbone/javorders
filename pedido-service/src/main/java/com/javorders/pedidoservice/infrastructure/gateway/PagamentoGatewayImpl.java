package com.javorders.pedidoservice.infrastructure.gateway;

import com.javorders.pedidoservice.domain.gateways.PagamentoGateway;
import com.javorders.pedidoservice.domain.model.Pedido;
import com.javorders.pedidoservice.infrastructure.dto.PagamentoDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class PagamentoGatewayImpl implements PagamentoGateway {

    private final WebClient webClient;

    public PagamentoGatewayImpl(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://pagamento-service:8084")
                .build();
    }

    @Override
    public PagamentoDTO solicitarPagamento(Pedido pedido) {
        return webClient.post()
                .uri("/pagamentos")
                .bodyValue(pedido)
                .retrieve()
                .bodyToMono(PagamentoDTO.class)
                .block();
    }

    @Override
    public void estornar(Pedido pedido) {
        webClient.post()
                .uri("/pagamentos/estornar")
                .bodyValue(pedido)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

}
