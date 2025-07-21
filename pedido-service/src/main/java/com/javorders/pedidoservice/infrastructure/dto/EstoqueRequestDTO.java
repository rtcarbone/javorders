package com.javorders.pedidoservice.infrastructure.dto;

import java.io.Serializable;

public record EstoqueRequestDTO(String sku,
                                Integer quantidade) implements Serializable {
}

