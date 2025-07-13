package com.javorders.pedidoservice.infrastructure.dto;

import com.javorders.pedidoservice.domain.model.StatusPedido;

import java.math.BigDecimal;
import java.util.List;

public record PedidoResponseDTO(
        Long id,
        Long clienteId,
        List<ItemPedidoDTO> itens,
        BigDecimal valorTotal,
        StatusPedido status
) {
}
