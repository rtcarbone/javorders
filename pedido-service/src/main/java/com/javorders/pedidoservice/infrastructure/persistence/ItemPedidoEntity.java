package com.javorders.pedidoservice.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;

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