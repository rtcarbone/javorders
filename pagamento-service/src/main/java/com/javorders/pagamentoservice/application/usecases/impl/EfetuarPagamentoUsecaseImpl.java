package com.javorders.pagamentoservice.application.usecases.impl;

import com.javorders.pagamentoservice.application.usecases.EfetuarPagamentoUsecase;
import com.javorders.pagamentoservice.domain.gateways.PagamentoGateway;
import com.javorders.pagamentoservice.domain.gateways.SistemaPagamentoExternoGateway;
import com.javorders.pagamentoservice.domain.model.Pagamento;
import com.javorders.pagamentoservice.domain.model.StatusPagamento;

public class EfetuarPagamentoUsecaseImpl implements EfetuarPagamentoUsecase {

    private final SistemaPagamentoExternoGateway externoGateway;
    private final PagamentoGateway pagamentoGateway;

    public EfetuarPagamentoUsecaseImpl(SistemaPagamentoExternoGateway externoGateway, PagamentoGateway pagamentoGateway) {
        this.externoGateway = externoGateway;
        this.pagamentoGateway = pagamentoGateway;
    }

    @Override
    public Pagamento executar(Pagamento pagamento) {
        var uuid = externoGateway.solicitarPagamento(pagamento);
        pagamento.setUuidTransacao(uuid);
        pagamento.setStatus(uuid.toString().endsWith("0") ? StatusPagamento.RECUSADO_SEM_CREDITO : StatusPagamento.APROVADO);
        return pagamentoGateway.salvar(pagamento);
    }
}