package com.javorders.pedidoreceiver.infrastructure.gateways;

import com.javorders.pedidoreceiver.domain.gateways.ProdutoGateway;
import com.javorders.pedidoreceiver.domain.model.ItemPedido;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProdutoGatewayImpl implements ProdutoGateway {

    private final WebClient webClient;

    public ProdutoGatewayImpl(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://produto-service:8082").build();
    }

    @Override
    public List<ProdutoDTO> obterBySkus(List<ItemPedido> itens) {
        List<String> skus = itens.stream().map(ItemPedido::getSku).toList();
        return webClient.post()
                .uri("/produtos/por-skus")
                .bodyValue(skus)
                .retrieve()
                .bodyToFlux(ProdutoDTO.class)
                .collectList()
                .block();
    }
}