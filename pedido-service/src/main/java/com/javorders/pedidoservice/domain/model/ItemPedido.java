package com.javorders.pedidoservice.domain.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedido {
    private String sku;
    private Integer quantidade;
}