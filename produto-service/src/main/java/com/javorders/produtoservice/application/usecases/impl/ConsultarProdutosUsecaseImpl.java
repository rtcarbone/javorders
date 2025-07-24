package com.javorders.produtoservice.application.usecases.impl;

import com.javorders.produtoservice.application.usecases.ConsultarProdutosUsecase;
import com.javorders.produtoservice.domain.gateways.ProdutoGateway;
import com.javorders.produtoservice.domain.model.Produto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultarProdutosUsecaseImpl implements ConsultarProdutosUsecase {

    private final ProdutoGateway gateway;

    @Override
    public List<Produto> executar() {
        return gateway.listarTodos();
    }

}
