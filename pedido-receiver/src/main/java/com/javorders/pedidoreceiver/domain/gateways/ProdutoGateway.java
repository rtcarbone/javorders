package com.javorders.pedidoreceiver.domain.gateways;

import com.javorders.pedidoreceiver.domain.dto.ProdutoDTO;
import com.javorders.pedidoreceiver.domain.model.ItemPedido;

import java.util.List;

public interface ProdutoGateway {
    List<ProdutoDTO> obterBySkus(List<ItemPedido> itens);
}