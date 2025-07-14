package com.javorders.pedidoservice.infrastructure.dto;

import java.util.List;

public record PedidoRequestDTO(
        Long clienteId,
        String numeroCartao,
        List<ItemPedidoDTO> itens
) {
}
