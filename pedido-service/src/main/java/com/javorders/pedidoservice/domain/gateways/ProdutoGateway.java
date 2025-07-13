package com.javorders.pedidoservice.domain.gateways;

import com.javorders.pedidoservice.domain.model.ItemPedido;
import com.javorders.pedidoservice.infrastructure.dto.ProdutoDTO;

import java.util.List;

public interface ProdutoGateway {
    List<ProdutoDTO> obterPorSkus(List<ItemPedido> itens);
}