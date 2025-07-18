package com.javorders.pagamentoservice.domain.ports.out;

import com.javorders.pagamentoservice.domain.model.Pagamento;

import java.util.UUID;

public interface PagamentoExternoGateway {
    UUID solicitarPagamento(Pagamento pagamento);
}
