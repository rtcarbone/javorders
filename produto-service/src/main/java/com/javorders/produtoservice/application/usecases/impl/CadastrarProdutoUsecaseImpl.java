package com.javorders.produtoservice.application.usecases.impl;

import com.javorders.produtoservice.application.usecases.CadastrarProdutoUsecase;
import com.javorders.produtoservice.domain.gateways.ProdutoGateway;
import com.javorders.produtoservice.domain.model.Produto;
import org.springframework.stereotype.Service;

@Service
public class CadastrarProdutoUsecaseImpl implements CadastrarProdutoUsecase {

    private final ProdutoGateway produtoGateway;

    public CadastrarProdutoUsecaseImpl(ProdutoGateway produtoGateway) {
        this.produtoGateway = produtoGateway;
    }

    @Override
    public Produto executar(Produto produto) {
        return produtoGateway.salvar(produto);
    }

}