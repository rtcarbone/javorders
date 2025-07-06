package com.javorders.pedidoreceiver.infrastructure.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
    private Long clienteId;
    private List<ItemPedidoDTO> itens;
    private PagamentoDTO pagamento;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemPedidoDTO {
        private String sku;
        private Integer quantidade;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PagamentoDTO {
        private String numeroCartao;
    }
}