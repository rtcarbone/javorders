package com.javorders.pedidoreceiver.infrastructure.controller.dto;

import java.util.List;

public record PedidoRequestDTO(
        Long clienteId,
        String numeroCartaoCredito,
        List<ItemPedidoDTO> itens
) {
}
