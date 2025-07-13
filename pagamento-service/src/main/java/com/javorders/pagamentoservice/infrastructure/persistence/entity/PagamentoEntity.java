package com.javorders.pagamentoservice.infrastructure.persistence.entity;

import com.javorders.pagamentoservice.domain.model.StatusPagamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "pagamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clienteId;

    private BigDecimal valor;

    private UUID uuidTransacao;

    @Enumerated(EnumType.STRING)
    private StatusPagamento status;

}