package com.javorders.clienteservice.infrastructure.persistence.repository;

import com.javorders.clienteservice.infrastructure.persistence.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<EnderecoEntity, Long> {
}