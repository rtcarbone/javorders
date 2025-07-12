package com.javorders.produtoservice.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "produtos", uniqueConstraints = {@UniqueConstraint(columnNames = "sku")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(nullable = false, unique = true)
    private String sku;

    private BigDecimal preco;

}