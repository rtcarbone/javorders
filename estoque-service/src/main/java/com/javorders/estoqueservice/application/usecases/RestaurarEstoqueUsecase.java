package com.javorders.estoqueservice.application.usecases;

public interface RestaurarEstoqueUsecase {
    void executar(String sku, Integer quantidade);
}
