package com.javorders.produtoservice.application.usecases;

import com.javorders.produtoservice.domain.model.Produto;

public interface CadastrarProdutoUsecase {
    Produto executar(Produto produto);
}