package com.javorders.pedidoservice.application.usecases.impl;

import com.javorders.pedidoservice.application.usecases.ProcessarNotificacaoPagamentoUsecase;
import com.javorders.pedidoservice.domain.gateways.EstoqueGateway;
import com.javorders.pedidoservice.domain.gateways.PagamentoGateway;
import com.javorders.pedidoservice.domain.gateways.PedidoGateway;
import com.javorders.pedidoservice.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProcessarNotificacaoPagamentoUsecaseImpl implements ProcessarNotificacaoPagamentoUsecase {

    private final PedidoGateway pedidoGateway;
    private final EstoqueGateway estoqueGateway;
    private final PagamentoGateway pagamentoGateway;

    @Override
    public void executar(UUID uuidTransacao) {
        Pagamento pagamento = pagamentoGateway.buscarPorUuid(uuidTransacao)
                .orElseThrow(() -> new IllegalArgumentException("Pagamento não encontrado"));

        Pedido pedido = pedidoGateway.buscarPorUuidTransacao(uuidTransacao)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));

        if (StatusPagamento.RECUSADO.equals(pagamento.getStatus())) {
            for (ItemPedido item : pedido.getItens()) {
                estoqueGateway.reporEstoque(item.getSku(), item.getQuantidade());
            }

            pedido = pedido.toBuilder()
                    .status(StatusPedido.FECHADO_SEM_CREDITO)
                    .build();

        } else if (StatusPagamento.APROVADO.equals(pagamento.getStatus())) {
            pedido = pedido.toBuilder()
                    .status(StatusPedido.FECHADO_COM_SUCESSO)
                    .build();
        }

        pedidoGateway.salvar(pedido);
    }
}