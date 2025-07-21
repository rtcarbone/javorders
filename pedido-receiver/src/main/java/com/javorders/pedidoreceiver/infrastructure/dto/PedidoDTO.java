package com.javorders.pedidoreceiver.infrastructure.dto;

import java.io.Serializable;
import java.util.List;

public record PedidoDTO(
        Long clienteId,
        String numeroCartao,
        List<ItemPedidoDTO> itens
) implements Serializable {
}
