package com.javorders.produtoservice.application.usecases.impl;

import com.javorders.produtoservice.application.usecases.DeletarProdutoUsecase;
import com.javorders.produtoservice.domain.gateways.ProdutoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletarProdutoUsecaseImpl implements DeletarProdutoUsecase {

    private final ProdutoGateway gateway;

    @Override
    public void executar(Long id) {
        gateway.deletar(id);
    }

}
