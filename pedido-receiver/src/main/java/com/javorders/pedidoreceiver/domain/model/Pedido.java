package com.javorders.pedidoreceiver.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(toBuilder = true)
public class Pedido {
    private Long id;
    private Long clienteId;
    private String numeroCartao;
    private List<ItemPedido> itens;
}
