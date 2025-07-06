package com.javorders.pagamentoservice.application.usecases;

import com.javorders.pagamentoservice.domain.model.Pagamento;

public interface EfetuarPagamentoUsecase {
    Pagamento executar(Pagamento pagamento);
}