package com.javorders.pedidoservice.infrastructure.gateway;

import com.javorders.pedidoservice.domain.gateways.ProdutoGateway;
import com.javorders.pedidoservice.domain.model.ItemPedido;
import com.javorders.pedidoservice.domain.model.Produto;
import com.javorders.pedidoservice.infrastructure.dto.ProdutoDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class ProdutoGatewayImpl implements ProdutoGateway {

    private final WebClient webClient;

    public ProdutoGatewayImpl(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://produto-service:8080")
                .build();
    }

    @Override
    public List<Produto> obterPorSkus(List<ItemPedido> itens) {
        List<String> skus = itens.stream()
                .map(ItemPedido::getSku)
                .toList();

        return webClient.post()
                .uri("/produtos/por-skus")
                .bodyValue(skus)
                .retrieve()
                .bodyToFlux(ProdutoDTO.class)
                .map(dto -> new Produto(dto.sku(), dto.preco()))
                .collectList()
                .block();
    }

}