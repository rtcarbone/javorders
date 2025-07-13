package com.javorders.pagamentoservice.domain.gateways;

import com.javorders.pagamentoservice.domain.model.Pagamento;

import java.util.UUID;

public interface PagamentoGateway {
    Pagamento salvar(Pagamento pagamento);

    void estornar(UUID uuidTransacao);
}