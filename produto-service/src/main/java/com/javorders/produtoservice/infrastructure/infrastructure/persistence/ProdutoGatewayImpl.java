package com.javorders.produtoservice.infrastructure.persistence;

import com.javorders.produtoservice.domain.gateways.ProdutoGateway;
import com.javorders.produtoservice.domain.model.Produto;
import com.javorders.produtoservice.infrastructure.persistence.mapper.ProdutoMapper;
import com.javorders.produtoservice.infrastructure.persistence.repository.ProdutoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProdutoGatewayImpl implements ProdutoGateway {

    private final ProdutoRepository produtoRepository;

    public ProdutoGatewayImpl(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public Produto salvar(Produto produto) {
        var entity = ProdutoMapper.toEntity(produto);
        return ProdutoMapper.toDomain(produtoRepository.save(entity));
    }

    @Override
    public Optional<Produto> buscarPorSku(String sku) {
        return produtoRepository.findBySku(sku).map(ProdutoMapper::toDomain);
    }

    @Override
    public List<Produto> listarTodos() {
        return produtoRepository.findAll().stream().map(ProdutoMapper::toDomain).collect(Collectors.toList());
    }
}