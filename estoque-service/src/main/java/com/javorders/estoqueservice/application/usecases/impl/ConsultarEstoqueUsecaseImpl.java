package com.javorders.estoqueservice.application.usecases.impl;

import com.javorders.estoqueservice.application.usecases.ConsultarEstoqueUsecase;
import com.javorders.estoqueservice.domain.gateways.EstoqueGateway;
import com.javorders.estoqueservice.domain.model.Estoque;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConsultarEstoqueUsecaseImpl implements ConsultarEstoqueUsecase {

    private final EstoqueGateway estoqueGateway;

    @Override
    public Optional<Estoque> executar(String sku) {
        return estoqueGateway.buscarPorSku(sku);
    }

}
