package com.javorders.estoqueservice.infrastructure.controller.mapper;

import com.javorders.estoqueservice.domain.model.Estoque;
import com.javorders.estoqueservice.infrastructure.controller.dto.EstoqueRequestDTO;
import com.javorders.estoqueservice.infrastructure.controller.dto.EstoqueResponseDTO;

public class EstoqueDTOMapper {

    public static Estoque toDomain(EstoqueRequestDTO dto) {
        return Estoque.builder()
                .sku(dto.sku())
                .quantidade(dto.quantidade())
                .build();
    }

    public static EstoqueResponseDTO toResponse(Estoque estoque) {
        return new EstoqueResponseDTO(
                estoque.getId(),
                estoque.getSku(),
                estoque.getQuantidade()
        );
    }

}
