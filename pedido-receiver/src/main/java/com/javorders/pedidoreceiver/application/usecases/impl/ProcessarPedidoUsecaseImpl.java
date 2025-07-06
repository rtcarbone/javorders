package com.javorders.pedidoreceiver.application.usecases.impl;

import com.javorders.pedidoreceiver.application.usecases.ProcessarPedidoUsecase;
import com.javorders.pedidoreceiver.domain.model.Pedido;
import com.javorders.pedidoreceiver.domain.gateways.*;

public class ProcessarPedidoUsecaseImpl implements ProcessarPedidoUsecase {

    private final ProdutoGateway produtoGateway;
    private final ClienteGateway clienteGateway;
    private final EstoqueGateway estoqueGateway;
    private final PagamentoGateway pagamentoGateway;
    private final PedidoGateway pedidoGateway;

    public ProcessarPedidoUsecaseImpl(ProdutoGateway produtoGateway,
                                       ClienteGateway clienteGateway,
                                       EstoqueGateway estoqueGateway,
                                       PagamentoGateway pagamentoGateway,
                                       PedidoGateway pedidoGateway) {
        this.produtoGateway = produtoGateway;
        this.clienteGateway = clienteGateway;
        this.estoqueGateway = estoqueGateway;
        this.pagamentoGateway = pagamentoGateway;
        this.pedidoGateway = pedidoGateway;
    }

    @Override
    public void processar(Pedido pedido) {
        var produtos = produtoGateway.obterBySkus(pedido.getItens());
        var cliente = clienteGateway.getById(pedido.getClienteId());

        estoqueGateway.baixaEstoque(pedido);
        var uuidPagamento = pagamentoGateway.criarOrdemPagamento(pedido);
        pedido.setValorTotal(produtos.stream()
            .map(p -> p.getPreco().multiply(
                pedido.getItens().stream()
                    .filter(i -> i.getSku().equals(p.getSku()))
                    .findFirst().get()
                    .getQuantidade().toBigDecimal()
            ))
            .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add)
        );

        pedidoGateway.salvar(pedido);
    }
}