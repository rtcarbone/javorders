package com.javorders.pagamentoservice.domain.gateways;

import com.javorders.pagamentoservice.domain.model.Pagamento;
import com.javorders.pagamentoservice.infrastructure.persistence.entity.PagamentoEntity;

import java.util.Optional;
import java.util.UUID;

public interface PagamentoGateway {
    Pagamento salvar(Pagamento pagamento);

    Optional<Pagamento> findByUuidTransacao(UUID uuidTransacao);
}