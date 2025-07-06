package com.javorders.pagamentoservice.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pagamento {
    private Long id;
    private Long clienteId;
    private BigDecimal valor;
    private UUID uuidTransacao;
    private StatusPagamento status;
}