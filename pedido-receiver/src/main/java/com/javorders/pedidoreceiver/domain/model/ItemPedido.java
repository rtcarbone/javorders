package com.javorders.pedidoreceiver.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemPedido {
    private String sku;
    private int quantidade;
}
