package com.javorders.pagamentoservice.infrastructure.mapper;

import com.javorders.pagamentoservice.domain.model.Pagamento;
import com.javorders.pagamentoservice.infrastructure.dto.PagamentoRequestDTO;
import com.javorders.pagamentoservice.infrastructure.dto.PagamentoResponseDTO;

public class PagamentoDTOMapper {

    public static Pagamento toDomain(PagamentoRequestDTO dto) {
        return Pagamento.builder()
                .clienteId(dto.clienteId())
                .valor(dto.valor())
                .numeroCartao(dto.numeroCartao())
                .build();
    }

    public static PagamentoResponseDTO toResponse(Pagamento pagamento) {
        return new PagamentoResponseDTO(
                pagamento.getUuidTransacao(),
                pagamento.getStatus()
        );
    }
}
