package com.javorders.pagamentoservice.domain.ports.out;

import com.javorders.pagamentoservice.domain.model.Pagamento;
import com.javorders.pagamentoservice.domain.model.StatusPagamento;

import java.util.UUID;

public interface PagamentoExternoGateway {
    UUID solicitarPagamento(Pagamento pagamento);

    StatusPagamento buscarPagamento(UUID uuidPagamento);
}
