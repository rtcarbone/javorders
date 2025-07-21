package com.javorders.pedidoreceiver.infrastructure.dto;

import java.io.Serializable;

public record ItemPedidoDTO(
        String sku,
        int quantidade
) implements Serializable {
}
