package com.javorders.pedidoservice.domain.gateways;

public interface ProdutoGateway {
    List<Produto> buscarPorSkus(List<String> skus);
}