package com.javorders.estoqueservice.application.usecases;

import com.javorders.estoqueservice.domain.model.Estoque;

public interface CadastrarEstoqueUsecase {
    Estoque executar(Estoque estoque);
}