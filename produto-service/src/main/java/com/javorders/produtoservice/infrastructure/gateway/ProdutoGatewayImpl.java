package com.javorders.produtoservice.infrastructure.gateway;

import com.javorders.produtoservice.domain.gateways.ProdutoGateway;
import com.javorders.produtoservice.domain.model.Produto;
import com.javorders.produtoservice.infrastructure.persistence.mapper.ProdutoMapper;
import com.javorders.produtoservice.infrastructure.persistence.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProdutoGatewayImpl implements ProdutoGateway {

    private final ProdutoRepository repository;

    @Override
    public Produto salvar(Produto produto) {
        var entity = ProdutoMapper.toEntity(produto);
        var salvo = repository.save(entity);
        return ProdutoMapper.toDomain(salvo);
    }

    @Override
    public Optional<Produto> buscarPorSku(String sku) {
        return repository.findBySku(sku)
                .map(ProdutoMapper::toDomain);
    }

    @Override
    public List<Produto> listarTodos() {
        return repository.findAll()
                .stream()
                .map(ProdutoMapper::toDomain)
                .toList();
    }

    @Override
    public List<Produto> buscarPorSkus(List<String> skus) {
        return repository.findBySkuIn(skus)
                .stream()
                .map(ProdutoMapper::toDomain)
                .toList();
    }

    @Override
    public void deletar(Long id) {
        repository.deleteById(id);
    }

}