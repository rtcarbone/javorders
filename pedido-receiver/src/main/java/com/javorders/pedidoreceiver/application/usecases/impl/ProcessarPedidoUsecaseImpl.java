package com.javorders.pedidoreceiver.application.usecases.impl;

import com.javorders.pedidoreceiver.application.usecases.ProcessarPedidoUsecase;
import com.javorders.pedidoreceiver.domain.gateways.*;
import com.javorders.pedidoreceiver.domain.model.ItemPedido;
import com.javorders.pedidoreceiver.domain.model.Pedido;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

@Service
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
        var pagamento = pagamentoGateway.criarOrdemPagamento(pedido);

        // Mapeia as quantidades por SKU
        Map<String, Integer> quantidadePorSku = pedido.getItens()
                .stream()
                .collect(Collectors.toMap(ItemPedido::getSku, ItemPedido::getQuantidade));

        // Calcula o valor total
        BigDecimal total = produtos.stream()
                .map(produto -> {
                    Integer quantidade = quantidadePorSku.getOrDefault(produto.getSku(), 0);
                    return produto.getPreco()
                            .multiply(new BigDecimal(quantidade));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        pedido.setValorTotal(total);
        pedidoGateway.salvar(pedido);
    }
}