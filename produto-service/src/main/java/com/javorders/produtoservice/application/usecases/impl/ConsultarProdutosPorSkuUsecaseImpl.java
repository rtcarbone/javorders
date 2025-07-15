package com.javorders.produtoservice.application.usecases.impl;

import com.javorders.produtoservice.application.usecases.ConsultarProdutosPorSkuUsecase;
import com.javorders.produtoservice.domain.gateways.ProdutoGateway;
import com.javorders.produtoservice.domain.model.Produto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultarProdutosPorSkuUsecaseImpl implements ConsultarProdutosPorSkuUsecase {

    private final ProdutoGateway produtoGateway;

    @Override
    public List<Produto> executar(List<String> skus) {
        return produtoGateway.buscarPorSkus(skus);
    }

}
