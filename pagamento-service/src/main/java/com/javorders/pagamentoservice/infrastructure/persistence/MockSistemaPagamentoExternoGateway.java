package com.javorders.pagamentoservice.infrastructure.persistence;

import com.javorders.pagamentoservice.domain.gateways.SistemaPagamentoExternoGateway;
import com.javorders.pagamentoservice.domain.model.Pagamento;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MockSistemaPagamentoExternoGateway implements SistemaPagamentoExternoGateway {

    @Override
    public UUID solicitarPagamento(Pagamento pagamento) {
        // Simula a geração de UUID externo
        return UUID.randomUUID();
    }

}
