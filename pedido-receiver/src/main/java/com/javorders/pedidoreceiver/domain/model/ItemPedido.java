package com.javorders.pedidoreceiver.domain.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedido {
    private String sku;
    private Integer quantidade;
}