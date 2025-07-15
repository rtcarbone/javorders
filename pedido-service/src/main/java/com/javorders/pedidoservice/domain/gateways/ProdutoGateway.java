package com.javorders.pedidoservice.domain.gateways;

import com.javorders.pedidoservice.domain.model.ItemPedido;
import com.javorders.pedidoservice.domain.model.Produto;

import java.util.List;

public interface ProdutoGateway {
    List<Produto> obterPorSkus(List<ItemPedido> itens);
}