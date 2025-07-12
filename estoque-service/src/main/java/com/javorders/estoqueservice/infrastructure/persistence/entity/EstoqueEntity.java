package com.javorders.estoqueservice.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "estoque")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstoqueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String sku;

    @Column(nullable = false)
    private Integer quantidade;

}