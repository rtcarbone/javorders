package com.javorders.pagamentoservice.application.usecases.impl;

import com.javorders.pagamentoservice.application.usecases.SolicitarPagamentoUsecase;
import com.javorders.pagamentoservice.domain.gateways.PagamentoGateway;
import com.javorders.pagamentoservice.domain.model.Pagamento;
import com.javorders.pagamentoservice.domain.model.StatusPagamento;
import com.javorders.pagamentoservice.domain.ports.out.PagamentoExternoGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SolicitarPagamentoUsecaseImpl implements SolicitarPagamentoUsecase {

    private final PagamentoExternoGateway externoGateway;
    private final PagamentoGateway pagamentoGateway;

    @Override
    public Pagamento executar(Pagamento pagamento) {
        var uuid = externoGateway.solicitarPagamento(pagamento);
        var pagamentoAtualizado = pagamento.toBuilder()
                .uuidTransacao(uuid)
                .status(StatusPagamento.PENDENTE)
                .build();
        return pagamentoGateway.salvar(pagamentoAtualizado);
    }

}