package com.javorders.produtoservice.application.usecases;

import com.javorders.produtoservice.domain.model.Produto;

import java.util.List;

public interface ConsultarProdutosUsecase {
    List<Produto> executar();
}
