package com.javorders.estoqueservice.application.usecases.impl;

import com.javorders.estoqueservice.application.usecases.CadastrarEstoqueUsecase;
import com.javorders.estoqueservice.domain.gateways.EstoqueGateway;
import com.javorders.estoqueservice.domain.model.Estoque;
import org.springframework.stereotype.Service;

@Service
public class CadastrarEstoqueUsecaseImpl implements CadastrarEstoqueUsecase {

    private final EstoqueGateway gateway;

    public CadastrarEstoqueUsecaseImpl(EstoqueGateway gateway) {
        this.gateway = gateway;
    }

    public Estoque executar(Estoque estoque) {
        boolean skuJaExiste = gateway.buscarPorSku(estoque.getSku())
                .isPresent();
        if (skuJaExiste) {
            throw new IllegalArgumentException("Já existe um estoque cadastrado com o SKU informado.");
        }
        return gateway.salvar(estoque);
    }

}