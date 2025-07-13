package com.javorders.pagamentoservice.application.usecases.impl;

import com.javorders.pagamentoservice.application.usecases.EfetuarPagamentoUsecase;
import com.javorders.pagamentoservice.domain.gateways.PagamentoGateway;
import com.javorders.pagamentoservice.domain.gateways.SistemaPagamentoExternoGateway;
import com.javorders.pagamentoservice.domain.model.Pagamento;
import com.javorders.pagamentoservice.domain.model.StatusPagamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EfetuarPagamentoUsecaseImpl implements EfetuarPagamentoUsecase {

    private final SistemaPagamentoExternoGateway externoGateway;
    private final PagamentoGateway pagamentoGateway;

    @Override
    public Pagamento executar(Pagamento pagamento) {
        var uuid = externoGateway.solicitarPagamento(pagamento);
        pagamento.setUuidTransacao(uuid);
        pagamento.setStatus(uuid.toString()
                                    .endsWith("0") ? StatusPagamento.RECUSADO : StatusPagamento.APROVADO);
        return pagamentoGateway.salvar(pagamento);
    }

}