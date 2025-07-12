package com.javorders.produtoservice.application.usecases.impl;

import com.javorders.produtoservice.application.usecases.CadastrarProdutoUsecase;
import com.javorders.produtoservice.domain.gateways.ProdutoGateway;
import com.javorders.produtoservice.domain.model.Produto;
import org.springframework.stereotype.Service;

@Service
public class CadastrarProdutoUsecaseImpl implements CadastrarProdutoUsecase {

    private final ProdutoGateway gateway;

    public CadastrarProdutoUsecaseImpl(ProdutoGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public Produto executar(Produto produto) {
        gateway.buscarPorSku(produto.getSku())
                .ifPresent(p -> {
                    throw new RuntimeException("Produto com SKU já existente.");
                });
        return gateway.salvar(produto);
    }

}