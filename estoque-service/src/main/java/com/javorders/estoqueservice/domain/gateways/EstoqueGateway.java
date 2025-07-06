package com.javorders.estoqueservice.domain.gateways;

import com.javorders.estoqueservice.domain.model.Estoque;

import java.util.Optional;
import java.util.List;

public interface EstoqueGateway {
    Estoque salvar(Estoque estoque);
    Optional<Estoque> buscarPorSku(String sku);
    List<Estoque> listarTodos();
}