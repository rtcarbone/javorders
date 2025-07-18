package com.javorders.pedidoservice.application.usecases;

import java.util.UUID;

public interface ProcessarNotificacaoPagamentoUsecase {
    void executar(UUID uuidTransacao);
}
