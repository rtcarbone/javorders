package com.javorders.pagamentoservice.domain.ports.out;

import com.javorders.pagamentoservice.domain.model.Pagamento;

import java.util.UUID;

public interface PagamentoExternoAdapter {
    UUID solicitarPagamento(Pagamento pagamento);
}
