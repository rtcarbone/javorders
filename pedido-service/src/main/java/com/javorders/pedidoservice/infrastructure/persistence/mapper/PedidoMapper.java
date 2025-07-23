package com.javorders.pedidoservice.infrastructure.persistence.mapper;

import com.javorders.pedidoservice.domain.model.ItemPedido;
import com.javorders.pedidoservice.domain.model.Pedido;
import com.javorders.pedidoservice.infrastructure.persistence.entity.ItemPedidoEntity;
import com.javorders.pedidoservice.infrastructure.persistence.entity.PedidoEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoMapper {

    public static PedidoEntity toEntity(Pedido pedido) {
        PedidoEntity entity = PedidoEntity.builder()
                .id(pedido.getId())
                .clienteId(pedido.getClienteId())
                .valorTotal(pedido.getValorTotal())
                .status(pedido.getStatus())
                .uuidTransacao(pedido.getUuidTransacao())
                .build();

        List<ItemPedidoEntity> itens = pedido.getItens()
                .stream()
                .map(i ->
                             ItemPedidoEntity.builder()
                                     .sku(i.getSku())
                                     .quantidade(i.getQuantidade())
                                     .pedido(entity)
                                     .build()
                )
                .collect(Collectors.toList());

        entity.setItens(itens);
        return entity;
    }

    public static Pedido toDomain(PedidoEntity entity) {
        return Pedido.builder()
                .id(entity.getId())
                .clienteId(entity.getClienteId())
                .valorTotal(entity.getValorTotal())
                .status(entity.getStatus())
                .uuidTransacao(entity.getUuidTransacao())
                .itens(entity.getItens()
                               .stream()
                               .map(i ->
                                            ItemPedido.builder()
                                                    .sku(i.getSku())
                                                    .quantidade(i.getQuantidade())
                                                    .build()
                               )
                               .collect(Collectors.toList()))
                .build();
    }
}