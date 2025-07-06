package com.javorders.pedidoreceiver.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
    private Long id;
    private Long clienteId;
    private BigDecimal valorTotal;
    private List<ItemPedido> itens;
}