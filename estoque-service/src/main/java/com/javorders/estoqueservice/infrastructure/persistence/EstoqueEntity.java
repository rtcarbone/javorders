package com.javorders.estoqueservice.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "estoques", uniqueConstraints = {@UniqueConstraint(columnNames = "sku")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstoqueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sku;
    private Integer quantidade;
}