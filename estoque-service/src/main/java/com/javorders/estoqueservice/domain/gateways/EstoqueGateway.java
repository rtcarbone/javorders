package com.javorders.estoqueservice.domain.gateways;

import com.javorders.estoqueservice.domain.model.Estoque;

import java.util.Optional;

public interface EstoqueGateway {

    Estoque salvar(Estoque estoque);

    Optional<Estoque> buscarPorSku(String sku);

    void baixarEstoque(String sku, Integer quantidade);

    void restaurarEstoque(String sku, Integer quantidade);

}