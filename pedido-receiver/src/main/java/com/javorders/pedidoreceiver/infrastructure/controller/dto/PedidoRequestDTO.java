package com.javorders.pedidoreceiver.infrastructure.controller.dto;

import java.util.List;

public record PedidoRequestDTO(
        Long clienteId,
        List<ItemPedidoDTO> itens
) {
}
