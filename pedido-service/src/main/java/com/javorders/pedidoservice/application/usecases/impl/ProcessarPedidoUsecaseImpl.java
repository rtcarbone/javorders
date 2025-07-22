package com.javorders.pedidoservice.application.usecases.impl;

import com.javorders.pedidoservice.application.usecases.ProcessarPedidoUsecase;
import com.javorders.pedidoservice.domain.gateways.*;
import com.javorders.pedidoservice.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessarPedidoUsecaseImpl implements ProcessarPedidoUsecase {

    private final ClienteGateway clienteGateway;
    private final ProdutoGateway produtoGateway;
    private final EstoqueGateway estoqueGateway;
    private final PagamentoGateway pagamentoGateway;
    private final PedidoGateway pedidoGateway;

    @Override
    public void executar(Pedido pedidoOriginal) {
        Cliente cliente = clienteGateway.buscarPorId(pedidoOriginal.getClienteId());
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado para o ID: " + pedidoOriginal.getClienteId());
        }

        var pedido = pedidoOriginal.toBuilder()
                .status(StatusPedido.ABERTO)
                .build();

        List<Produto> produtos = produtoGateway.obterPorSkus(pedido.getItens());
        if (produtos.isEmpty()) {
            throw new IllegalArgumentException("Nenhum produto encontrado para os SKUs fornecidos.");
        }

        pedido = Pedido.criarComValorCalculado(pedido.getClienteId(), pedido.getItens(), produtos, pedido.getNumeroCartao(), pedido.getStatus());

        List<ItemPedido> estoqueBaixadoComSucesso = new ArrayList<>();

        try {
            for (ItemPedido item : pedido.getItens()) {
                try {
                    estoqueGateway.baixarEstoque(item.getSku(), item.getQuantidade());
                    estoqueBaixadoComSucesso.add(item);
                } catch (Exception e) {
                    for (ItemPedido baixado : estoqueBaixadoComSucesso) {
                        estoqueGateway.reporEstoque(baixado.getSku(), baixado.getQuantidade());
                    }

                    pedido = pedido.toBuilder()
                            .status(StatusPedido.FECHADO_SEM_ESTOQUE)
                            .build();

                    pedidoGateway.salvar(pedido);
                    return;
                }
            }

            try {
                Pagamento pagamento = pagamentoGateway.solicitarPagamento(pedido);

                pedido = pedido.toBuilder()
                        .uuidTransacao(pagamento.getUuidTransacao())
                        .build();

            } catch (Exception e) {
                for (ItemPedido item : estoqueBaixadoComSucesso) {
                    estoqueGateway.reporEstoque(item.getSku(), item.getQuantidade());
                }
            }

        } catch (Exception e) {
            pedido = pedido.toBuilder()
                    .status(StatusPedido.FECHADO_SEM_ESTOQUE)
                    .build();
        }

        pedidoGateway.salvar(pedido);
    }

}