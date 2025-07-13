package com.javorders.pagamentoservice.application.usecases.impl;

import com.javorders.pagamentoservice.application.usecases.EstornarPagamentoUsecase;
import com.javorders.pagamentoservice.domain.gateways.PagamentoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EstornarPagamentoUsecaseImpl implements EstornarPagamentoUsecase {

    private final PagamentoGateway pagamentoGateway;

    @Override
    public void estornar(UUID uuidTransacao) {
        pagamentoGateway.estornar(uuidTransacao);
    }

}
