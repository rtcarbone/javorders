package com.javorders.produtoservice.application.usecases;

import com.javorders.produtoservice.domain.model.Produto;

public interface AlterarProdutoUsecase {
    Produto executar(Long id, Produto produto);
}
