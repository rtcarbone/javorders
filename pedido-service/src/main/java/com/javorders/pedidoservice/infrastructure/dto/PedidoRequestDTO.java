package com.javorders.pedidoservice.infrastructure.dto;

import java.util.List;

public record PedidoRequestDTO(
        Long clienteId,
        List<ItemPedidoDTO> itens
) {
}
