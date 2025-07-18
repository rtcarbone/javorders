package com.javorders.produtoservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Produto {
    private Long id;
    private String nome;
    private String sku;
    private BigDecimal preco;
}