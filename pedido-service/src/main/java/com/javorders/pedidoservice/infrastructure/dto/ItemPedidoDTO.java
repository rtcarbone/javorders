package com.javorders.pedidoservice.infrastructure.dto;

import java.io.Serializable;

public record ItemPedidoDTO(
        String sku,
        int quantidade
) implements Serializable {
}
