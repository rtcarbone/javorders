package com.javorders.pedidoservice.domain.model;

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
    private StatusPedido status;
    private List<ItemPedido> itens;
}