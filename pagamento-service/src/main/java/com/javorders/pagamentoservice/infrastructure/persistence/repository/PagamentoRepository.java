package com.javorders.pagamentoservice.infrastructure.persistence.repository;

import com.javorders.pagamentoservice.infrastructure.persistence.PagamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PagamentoRepository extends JpaRepository<PagamentoEntity, Long> {
    PagamentoEntity findByUuidTransacao(UUID uuidTransacao);
}