package com.javorders.pagamentoservice.infrastructure.persistence.mapper;

import com.javorders.pagamentoservice.domain.model.Pagamento;
import com.javorders.pagamentoservice.infrastructure.persistence.entity.PagamentoEntity;

public class PagamentoMapper {

    public static PagamentoEntity toEntity(Pagamento pagamento) {
        return PagamentoEntity.builder()
                .id(pagamento.getId())
                .clienteId(pagamento.getClienteId())
                .valor(pagamento.getValor())
                .uuidTransacao(pagamento.getUuidTransacao())
                .status(pagamento.getStatus())
                .build();
    }

    public static Pagamento toDomain(PagamentoEntity entity) {
        return Pagamento.builder()
                .id(entity.getId())
                .clienteId(entity.getClienteId())
                .valor(entity.getValor())
                .uuidTransacao(entity.getUuidTransacao())
                .status(entity.getStatus())
                .build();
    }
}