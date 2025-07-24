package com.javorders.produtoservice.application.usecases.impl;

import com.javorders.produtoservice.application.usecases.AlterarProdutoUsecase;
import com.javorders.produtoservice.domain.gateways.ProdutoGateway;
import com.javorders.produtoservice.domain.model.Produto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlterarProdutoUsecaseImpl implements AlterarProdutoUsecase {

    private final ProdutoGateway gateway;

    @Override
    public Produto executar(Long id, Produto produto) {
        gateway.buscarPorSku(produto.getSku())
                .ifPresent(p -> {
                    if (!p.getId()
                            .equals(id)) {
                        throw new RuntimeException("SKU já utilizado por outro produto.");
                    }
                });

        produto = Produto.builder()
                .id(id)
                .nome(produto.getNome())
                .sku(produto.getSku())
                .preco(produto.getPreco())
                .build();

        return gateway.salvar(produto);
    }
}
