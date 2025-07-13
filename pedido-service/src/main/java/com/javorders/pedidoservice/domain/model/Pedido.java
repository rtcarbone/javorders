package com.javorders.pedidoservice.domain.model;

import com.javorders.pedidoservice.infrastructure.dto.ProdutoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
    private Long id;
    private Long clienteId;
    private List<ItemPedido> itens;
    private BigDecimal valorTotal;
    private StatusPedido status;
    private String uuidTransacao;

    public BigDecimal calcularValorTotal(List<ProdutoDTO> produtos) {
        return itens.stream()
                .map(item -> {
                    ProdutoDTO produto = produtos.stream()
                            .filter(p -> p.sku()
                                    .equals(item.getSku()))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + item.getSku()));
                    return produto.preco()
                            .multiply(BigDecimal.valueOf(item.getQuantidade()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}