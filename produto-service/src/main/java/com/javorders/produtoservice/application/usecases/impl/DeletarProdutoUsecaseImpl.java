package com.javorders.produtoservice.application.usecases.impl;

import com.javorders.produtoservice.application.usecases.DeletarProdutoUsecase;
import com.javorders.produtoservice.domain.gateways.ProdutoGateway;
import org.springframework.stereotype.Service;

@Service
public class DeletarProdutoUsecaseImpl implements DeletarProdutoUsecase {

    private final ProdutoGateway gateway;

    public DeletarProdutoUsecaseImpl(ProdutoGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public void executar(Long id) {
        gateway.deletar(id);
    }

}
