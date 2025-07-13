package com.javorders.pedidoservice.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "itens_pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemPedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sku;

    private Integer quantidade;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private PedidoEntity pedido;

}