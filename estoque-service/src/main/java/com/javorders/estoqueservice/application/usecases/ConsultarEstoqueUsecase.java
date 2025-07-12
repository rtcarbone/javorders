package com.javorders.estoqueservice.application.usecases;

import com.javorders.estoqueservice.domain.model.Estoque;

import java.util.Optional;

public interface ConsultarEstoqueUsecase {
    Optional<Estoque> executar(String sku);
}
