package com.javorders.pagamentoservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Pagamento {
    private Long id;
    private Long clienteId;
    private BigDecimal valor;
    private UUID uuidTransacao;
    private String numeroCartao;
    private StatusPagamento status;
}