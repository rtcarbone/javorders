package com.javorders.pagamentoservice.application.usecases.impl;

import com.javorders.pagamentoservice.application.usecases.BuscarPagamentoPorUuidUsecase;
import com.javorders.pagamentoservice.domain.gateways.PagamentoGateway;
import com.javorders.pagamentoservice.domain.model.Pagamento;
import com.javorders.pagamentoservice.domain.model.StatusPagamento;
import com.javorders.pagamentoservice.domain.ports.out.PagamentoExternoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuscarPagamentoPorUuidUsecaseImpl implements BuscarPagamentoPorUuidUsecase {

    private final PagamentoExternoGateway externoGateway;
    private final PagamentoGateway pagamentoGateway;

    @Override
    public Pagamento executar(UUID uuid) {
        Pagamento pagamento = pagamentoGateway.findByUuidTransacao(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Pagamento não encontrado para UUID: " + uuid));

        StatusPagamento statusPagamento = externoGateway.buscarPagamento(uuid);

        var pagamentoAtualizado = pagamento.toBuilder()
                .status(statusPagamento)
                .build();

        return pagamentoGateway.salvar(pagamentoAtualizado);
    }

}
