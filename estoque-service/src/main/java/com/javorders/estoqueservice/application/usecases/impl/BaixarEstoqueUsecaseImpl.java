package com.javorders.estoqueservice.application.usecases.impl;

import com.javorders.estoqueservice.application.usecases.BaixarEstoqueUsecase;
import com.javorders.estoqueservice.domain.gateways.EstoqueGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BaixarEstoqueUsecaseImpl implements BaixarEstoqueUsecase {

    private final EstoqueGateway estoqueGateway;

    @Override
    public void executar(String sku, Integer quantidade) {
        estoqueGateway.baixarEstoque(sku, quantidade);
    }

}
