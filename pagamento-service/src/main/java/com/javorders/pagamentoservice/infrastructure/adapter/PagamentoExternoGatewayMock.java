package com.javorders.pagamentoservice.infrastructure.adapter;

import com.javorders.pagamentoservice.domain.model.Pagamento;
import com.javorders.pagamentoservice.domain.ports.out.PagamentoExternoGateway;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PagamentoExternoGatewayMock implements PagamentoExternoGateway {

    @Override
    public UUID solicitarPagamento(Pagamento pagamento) {
        // Simula a geração de UUID externo
        return UUID.randomUUID();
    }

}
