package com.javorders.pagamentoservice.infrastructure.persistence.repository;

import com.javorders.pagamentoservice.infrastructure.persistence.entity.PagamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PagamentoRepository extends JpaRepository<PagamentoEntity, Long> {
    Optional<PagamentoEntity> findByUuidTransacao(UUID uuidTransacao);
}