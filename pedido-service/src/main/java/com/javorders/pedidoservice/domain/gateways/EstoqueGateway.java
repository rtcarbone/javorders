package com.javorders.pedidoservice.domain.gateways;

public interface EstoqueGateway {
    void baixarEstoque(String sku, Integer quantidade);

    void reporEstoque(String sku, Integer quantidade);
}
