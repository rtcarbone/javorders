package com.javorders.pagamentoservice.application.usecases.impl;

import com.javorders.pagamentoservice.application.usecases.BuscarPagamentoPorUuidUsecase;
import com.javorders.pagamentoservice.domain.gateways.PagamentoGateway;
import com.javorders.pagamentoservice.domain.model.Pagamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuscarPagamentoPorUuidUsecaseImpl implements BuscarPagamentoPorUuidUsecase {

    private final PagamentoGateway pagamentoGateway;

    @Override
    public Pagamento executar(UUID uuid) {
        return pagamentoGateway.findByUuidTransacao(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Pagamento não encontrado para UUID: " + uuid));
    }

}
