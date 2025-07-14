package com.javorders.pedidoservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
    private Long id;
    private Long clienteId;
    private List<ItemPedido> itens;
    private BigDecimal valorTotal;
    private StatusPedido status;
    private String uuidTransacao;
}