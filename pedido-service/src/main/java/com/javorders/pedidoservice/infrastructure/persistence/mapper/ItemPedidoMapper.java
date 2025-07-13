package com.javorders.pedidoservice.infrastructure.persistence.mapper;

import com.javorders.pedidoservice.domain.model.ItemPedido;
import com.javorders.pedidoservice.infrastructure.persistence.entity.ItemPedidoEntity;
import com.javorders.pedidoservice.infrastructure.persistence.entity.PedidoEntity;

public class ItemPedidoMapper {

    public static ItemPedidoEntity toEntity(ItemPedido item, PedidoEntity pedido) {
        return ItemPedidoEntity.builder()
                .sku(item.getSku())
                .quantidade(item.getQuantidade())
                .pedido(pedido)
                .build();
    }

    public static ItemPedido toDomain(ItemPedidoEntity entity) {
        return ItemPedido.builder()
                .sku(entity.getSku())
                .quantidade(entity.getQuantidade())
                .build();
    }

}
