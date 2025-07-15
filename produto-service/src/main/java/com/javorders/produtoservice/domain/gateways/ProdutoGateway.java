package com.javorders.produtoservice.domain.gateways;

import com.javorders.produtoservice.domain.model.Produto;

import java.util.List;
import java.util.Optional;

public interface ProdutoGateway {
    Produto salvar(Produto produto);

    Optional<Produto> buscarPorSku(String sku);

    List<Produto> listarTodos();

    List<Produto> buscarPorSkus(List<String> skus);

    void deletar(Long id);
}