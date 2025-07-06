package com.javorders.estoqueservice.application.usecases.impl;

import com.javorders.estoqueservice.application.usecases.CadastrarEstoqueUsecase;
import com.javorders.estoqueservice.domain.gateways.EstoqueGateway;
import com.javorders.estoqueservice.domain.model.Estoque;

public class CadastrarEstoqueUsecaseImpl implements CadastrarEstoqueUsecase {

    private final EstoqueGateway gateway;

    public CadastrarEstoqueUsecaseImpl(EstoqueGateway gateway) {
        this.gateway = gateway;
    }

    public Estoque executar(Estoque estoque) {
        return gateway.salvar(estoque);
    }
}