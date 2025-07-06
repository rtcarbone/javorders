package com.javorders.produtoservice.infrastructure.persistence.repository;

import com.javorders.produtoservice.infrastructure.persistence.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {
    Optional<ProdutoEntity> findBySku(String sku);
}