package com.javorders.pagamentoservice.application.usecases;

import com.javorders.pagamentoservice.domain.model.Pagamento;

public interface SolicitarPagamentoUsecase {
    Pagamento executar(Pagamento pagamento);
}