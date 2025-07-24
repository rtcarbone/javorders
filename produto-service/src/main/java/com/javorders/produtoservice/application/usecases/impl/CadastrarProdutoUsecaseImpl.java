package com.javorders.produtoservice.application.usecases.impl;

import com.javorders.produtoservice.application.usecases.CadastrarProdutoUsecase;
import com.javorders.produtoservice.domain.gateways.ProdutoGateway;
import com.javorders.produtoservice.domain.model.Produto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CadastrarProdutoUsecaseImpl implements CadastrarProdutoUsecase {

    private final ProdutoGateway gateway;

    @Override
    public Produto executar(Produto produto) {
        boolean skuJaExiste = gateway.buscarPorSku(produto.getSku())
                .isPresent();
        if (skuJaExiste) {
            throw new IllegalArgumentException("Produto com SKU já existente.");
        }
        return gateway.salvar(produto);
    }

}