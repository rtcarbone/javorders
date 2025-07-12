package com.javorders.produtoservice.application.usecases.impl;

import com.javorders.produtoservice.application.usecases.ConsultarProdutosUsecase;
import com.javorders.produtoservice.domain.gateways.ProdutoGateway;
import com.javorders.produtoservice.domain.model.Produto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultarProdutosUsecaseImpl implements ConsultarProdutosUsecase {

    private final ProdutoGateway gateway;

    public ConsultarProdutosUsecaseImpl(ProdutoGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public List<Produto> executar() {
        return gateway.listarTodos();
    }

}
