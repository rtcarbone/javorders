package com.javorders.estoqueservice.domain.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Estoque {
    private Long id;
    private String sku;
    private Integer quantidade;
}