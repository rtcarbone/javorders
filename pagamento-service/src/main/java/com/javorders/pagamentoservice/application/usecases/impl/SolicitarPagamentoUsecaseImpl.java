package com.javorders.pagamentoservice.application.usecases.impl;

import com.javorders.pagamentoservice.application.usecases.SolicitarPagamentoUsecase;
import com.javorders.pagamentoservice.domain.gateways.PagamentoGateway;
import com.javorders.pagamentoservice.domain.model.Pagamento;
import com.javorders.pagamentoservice.domain.model.StatusPagamento;
import com.javorders.pagamentoservice.domain.ports.out.PagamentoExternoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SolicitarPagamentoUsecaseImpl implements SolicitarPagamentoUsecase {

    private final PagamentoExternoGateway externoGateway;
    private final PagamentoGateway pagamentoGateway;

    @Override
    public Pagamento executar(Pagamento pagamento) {
        var uuid = externoGateway.solicitarPagamento(pagamento);
        var pagamentoAtualizado = pagamento.toBuilder()
                .uuidTransacao(uuid)
                .status(uuid.toString()
                                .endsWith("0")
                                ? StatusPagamento.RECUSADO
                                : StatusPagamento.APROVADO)
                .build();
        return pagamentoGateway.salvar(pagamentoAtualizado);
    }

}