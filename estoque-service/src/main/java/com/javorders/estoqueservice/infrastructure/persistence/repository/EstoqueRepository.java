package com.javorders.estoqueservice.infrastructure.persistence.repository;

import com.javorders.estoqueservice.infrastructure.persistence.entity.EstoqueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstoqueRepository extends JpaRepository<EstoqueEntity, Long> {
    Optional<EstoqueEntity> findBySku(String sku);
}