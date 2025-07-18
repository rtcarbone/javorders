package com.javorders.estoqueservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Estoque {
    private Long id;
    private String sku;
    private Integer quantidade;
}