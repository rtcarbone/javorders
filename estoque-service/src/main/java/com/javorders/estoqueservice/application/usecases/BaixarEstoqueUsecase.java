package com.javorders.estoqueservice.application.usecases;

public interface BaixarEstoqueUsecase {
    void executar(String sku, Integer quantidade);
}
