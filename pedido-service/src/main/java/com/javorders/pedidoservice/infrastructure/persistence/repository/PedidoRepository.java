package com.javorders.pedidoservice.infrastructure.persistence.repository;

import com.javorders.pedidoservice.infrastructure.persistence.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {
}