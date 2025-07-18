package com.javorders.pagamentoservice.application.usecases;

import com.javorders.pagamentoservice.domain.model.Pagamento;

import java.util.UUID;

public interface BuscarPagamentoPorUuidUsecase {
    Pagamento executar(UUID uuid);
}
