package com.javorders.pedidoservice.infrastructure.persistence.repository;

import com.javorders.pedidoservice.infrastructure.persistence.entity.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {
    Optional<PedidoEntity> findById(Long id);

    Optional<PedidoEntity> findByUuidTransacao(UUID uuidTransacao);
}