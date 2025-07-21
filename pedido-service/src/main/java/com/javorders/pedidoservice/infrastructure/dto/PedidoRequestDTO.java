package com.javorders.pedidoservice.infrastructure.dto;

import java.io.Serializable;
import java.util.List;

public record PedidoRequestDTO(
        Long clienteId,
        String numeroCartao,
        List<ItemPedidoDTO> itens
) implements Serializable {
}
