package com.javorders.pedidoservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
    private Long id;
    private Long clienteId;
    private List<ItemPedido> itens;
    private BigDecimal valorTotal;
    private StatusPedido status;
    private String numeroCartao;
    private UUID uuidTransacao;

    public BigDecimal calcularValorTotal(List<Produto> produtos) {
        return itens.stream()
                .map(item -> {
                    Produto produto = produtos.stream()
                            .filter(p -> p.getSku()
                                    .equals(item.getSku()))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + item.getSku()));
                    return produto.getPreco()
                            .multiply(BigDecimal.valueOf(item.getQuantidade()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}