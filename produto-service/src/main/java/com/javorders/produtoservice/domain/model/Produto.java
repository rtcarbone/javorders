package com.javorders.produtoservice.domain.model;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Produto {
    private Long id;
    private String nome;
    private String sku;
    private BigDecimal preco;
}