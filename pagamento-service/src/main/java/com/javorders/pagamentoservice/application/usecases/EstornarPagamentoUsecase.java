package com.javorders.pagamentoservice.application.usecases;

import java.util.UUID;

public interface EstornarPagamentoUsecase {
    void estornar(UUID uuidTransacao);
}
