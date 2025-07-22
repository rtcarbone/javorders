package com.javorders.pedidoservice.infrastructure.mapper;

import com.javorders.pedidoservice.domain.model.ItemPedido;
import com.javorders.pedidoservice.domain.model.Pedido;
import com.javorders.pedidoservice.infrastructure.dto.ItemPedidoDTO;
import com.javorders.pedidoservice.infrastructure.dto.PedidoRequestDTO;
import com.javorders.pedidoservice.infrastructure.dto.PedidoResponseDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class PedidoDTOMapper {

    public static Pedido toDomain(PedidoRequestDTO dto) {
        List<ItemPedido> itens = dto.itens()
                .stream()
                .map(i -> ItemPedido.builder()
                        .sku(i.sku())
                        .quantidade(i.quantidade())
                        .build())
                .collect(Collectors.toList());

        BigDecimal total = BigDecimal.ZERO;
        return Pedido.builder()
                .clienteId(dto.clienteId())
                .itens(itens)
                .numeroCartao(dto.numeroCartao())
                .valorTotal(total)
                .build();
    }

    public static PedidoResponseDTO toResponse(Pedido pedido) {
        List<ItemPedidoDTO> itens = pedido.getItens()
                .stream()
                .map(i -> new ItemPedidoDTO(i.getSku(), i.getQuantidade()))
                .collect(Collectors.toList());

        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getClienteId(),
                itens,
                pedido.getValorTotal(),
                pedido.getStatus(),
                pedido.getUuidTransacao()
        );
    }

}
