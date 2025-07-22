package com.javorders.pedidoreceiver.infrastructure.mapper;

import com.javorders.pedidoreceiver.domain.model.ItemPedido;
import com.javorders.pedidoreceiver.domain.model.Pedido;
import com.javorders.pedidoreceiver.infrastructure.dto.ItemPedidoDTO;
import com.javorders.pedidoreceiver.infrastructure.dto.PedidoDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PedidoMapper {

    public Pedido toDomain(PedidoDTO dto) {
        return Pedido.builder()
                .clienteId(dto.clienteId())
                .numeroCartao(dto.numeroCartao())
                .itens(dto.itens()
                               .stream()
                               .map(i -> ItemPedido.builder()
                                       .sku(i.sku())
                                       .quantidade(i.quantidade())
                                       .build())
                               .collect(Collectors.toList()))
                .build();
    }

    public PedidoDTO toDTO(Pedido pedido) {
        return new PedidoDTO(
                pedido.getClienteId(),
                pedido.getNumeroCartao(),
                pedido.getItens()
                        .stream()
                        .map(i -> new ItemPedidoDTO(i.getSku(), i.getQuantidade()))
                        .toList()
        );
    }

}