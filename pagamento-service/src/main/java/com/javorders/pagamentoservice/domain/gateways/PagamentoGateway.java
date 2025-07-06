package com.javorders.pagamentoservice.domain.gateways;

import com.javorders.pagamentoservice.domain.model.Pagamento;

public interface PagamentoGateway {
    Pagamento salvar(Pagamento pagamento);
}