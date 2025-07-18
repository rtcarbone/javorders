package com.javorders.pedidoservice.application.usecases.impl;

import com.javorders.pedidoservice.application.usecases.ProcessarPedidoUsecase;
import com.javorders.pedidoservice.domain.gateways.*;
import com.javorders.pedidoservice.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

        BigDecimal valorTotal = pedido.calcularValorTotal(produtos);
        pedido = pedido.toBuilder()
                .valorTotal(valorTotal)
                .build();

        Pagamento pagamento = null;
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
                pagamento = pagamentoGateway.solicitarPagamento(pedido);

                if (StatusPagamento.RECUSADO.equals(pagamento.getStatus())) {
                    throw new RuntimeException("Pagamento recusado");
                }

                pedido = pedido.toBuilder()
                        .uuidTransacao(pagamento.getUuidTransacao())
                        .status(StatusPedido.FECHADO_COM_SUCESSO)
                        .build();

            } catch (Exception e) {
                for (ItemPedido item : estoqueBaixadoComSucesso) {
                    estoqueGateway.reporEstoque(item.getSku(), item.getQuantidade());
                }

                pedido = pedido.toBuilder()
                        .status(StatusPedido.FECHADO_SEM_CREDITO)
                        .build();
            }

        } catch (Exception e) {
            if (pagamento != null && StatusPagamento.APROVADO.equals(pagamento.getStatus())) {
                pagamentoGateway.estornar(pedido);
            }

            pedido = pedido.toBuilder()
                    .status(StatusPedido.FECHADO_SEM_ESTOQUE)
                    .build();
        }

        pedidoGateway.salvar(pedido);
    }

}