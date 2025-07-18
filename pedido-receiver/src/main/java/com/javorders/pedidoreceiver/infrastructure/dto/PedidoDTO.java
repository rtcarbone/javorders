package com.javorders.pedidoreceiver.infrastructure.dto;

import java.util.List;

public record PedidoDTO(
        Long clienteId,
        String numeroCartao,
        List<ItemPedidoDTO> itens
) {
}
