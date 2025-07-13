package com.javorders.pedidoreceiver.application.usecases.impl;

import com.javorders.pedidoreceiver.application.usecases.ProcessarPedidoUsecase;
import com.javorders.pedidoreceiver.domain.gateways.*;
import com.javorders.pedidoreceiver.domain.model.ItemPedido;
import com.javorders.pedidoreceiver.domain.model.Pedido;
import com.javorders.pedidoreceiver.domain.model.StatusPedido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProcessarPedidoUsecaseImpl implements ProcessarPedidoUsecase {

    private final ProdutoGateway produtoGateway;
    private final ClienteGateway clienteGateway;
    private final EstoqueGateway estoqueGateway;
    private final PagamentoGateway pagamentoGateway;
    private final PedidoGateway pedidoGateway;

    @Override
    public void processar(Pedido pedido) {
        var produtos = produtoGateway.obterBySkus(pedido.getItens());
        var cliente = clienteGateway.getById(pedido.getClienteId());

        if (cliente == null || produtos.isEmpty()) {
            pedido.setStatus(StatusPedido.FECHADO_SEM_CREDITO);
            pedidoGateway.salvar(pedido);
            return;
        }

        estoqueGateway.baixaEstoque(pedido);
        var uuidPagamento = pagamentoGateway.criarOrdemPagamento(pedido);

        BigDecimal valorTotal = produtos.stream()
                .map(p -> p.getPreco()
                        .multiply(
                                BigDecimal.valueOf(pedido.getItens()
                                                           .stream()
                                                           .filter(i -> i.getSku()
                                                                   .equals(p.getSku()))
                                                           .findFirst()
                                                           .map(ItemPedido::getQuantidade)
                                                           .orElse(0))))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        pedido.setValorTotal(valorTotal);
        pedido.setStatus(
                pagamentoRecusado(uuidPagamento)
                        ? StatusPedido.FECHADO_SEM_CREDITO
                        : StatusPedido.FECHADO_COM_SUCESSO
        );

        pedidoGateway.salvar(pedido);
    }

    private boolean pagamentoRecusado(UUID uuidPagamento) {
        return uuidPagamento.toString()
                .endsWith("0");
    }

}