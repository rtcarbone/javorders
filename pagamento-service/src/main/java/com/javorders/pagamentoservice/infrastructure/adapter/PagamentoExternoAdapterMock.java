package com.javorders.pagamentoservice.infrastructure.adapter;

import com.javorders.pagamentoservice.domain.model.Pagamento;
import com.javorders.pagamentoservice.domain.ports.out.PagamentoExternoAdapter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PagamentoExternoAdapterMock implements PagamentoExternoAdapter {

    @Override
    public UUID solicitarPagamento(Pagamento pagamento) {
        // Simula a geração de UUID externo
        return UUID.randomUUID();
    }

}
