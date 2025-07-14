package com.javorders.pedidoservice.application.usecases.impl;

import com.javorders.pedidoservice.application.usecases.ProcessarPedidoUsecase;
import com.javorders.pedidoservice.domain.gateways.*;
import com.javorders.pedidoservice.domain.model.ItemPedido;
import com.javorders.pedidoservice.domain.model.Pedido;
import com.javorders.pedidoservice.domain.model.StatusPedido;
import com.javorders.pedidoservice.infrastructure.dto.ClienteDTO;
import com.javorders.pedidoservice.infrastructure.dto.ProdutoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProcessarPedidoUsecaseImpl implements ProcessarPedidoUsecase {

    private final ClienteGateway clienteGateway;
    private final ProdutoGateway produtoGateway;
    private final EstoqueGateway estoqueGateway;
    private final PagamentoGateway pagamentoGateway;
    private final PedidoGateway pedidoGateway;

    @Override
    public void executar(Pedido pedido) {

        ClienteDTO cliente = clienteGateway.buscarPorId(pedido.getClienteId());
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado para o ID: " + pedido.getClienteId());
        }

        pedido.setStatus(StatusPedido.ABERTO);

        List<ProdutoDTO> produtos = produtoGateway.obterPorSkus(pedido.getItens());
        if (produtos.isEmpty()) {
            throw new IllegalArgumentException("Nenhum produto encontrado para os SKUs fornecidos.");
        }

        BigDecimal valorTotal = calcularValorTotal(pedido, produtos);
        pedido.setValorTotal(valorTotal);

        UUID uuidPagamento = null;

        // Rastreia os itens cujo estoque foi baixado com sucesso
        List<ItemPedido> estoqueBaixadoComSucesso = new ArrayList<>();

        try {
            for (ItemPedido item : pedido.getItens()) {
                try {
                    estoqueGateway.baixarEstoque(item.getSku(), item.getQuantidade());
                    estoqueBaixadoComSucesso.add(item);
                } catch (Exception e) {
                    // Repor o que já foi baixado
                    for (ItemPedido baixado : estoqueBaixadoComSucesso) {
                        estoqueGateway.reporEstoque(baixado.getSku(), baixado.getQuantidade());
                    }

                    pedido.setStatus(StatusPedido.FECHADO_SEM_ESTOQUE);
                    pedidoGateway.salvar(pedido);
                    return;
                }
            }

            // Tentativa de pagamento
            try {
                uuidPagamento = pagamentoGateway.solicitarPagamento(pedido);
                pedido.setUuidTransacao(uuidPagamento.toString());
                pedido.setStatus(StatusPedido.FECHADO_COM_SUCESSO);

            } catch (Exception e) {
                // Repor o estoque de todos os itens
                for (ItemPedido item : estoqueBaixadoComSucesso) {
                    estoqueGateway.reporEstoque(item.getSku(), item.getQuantidade());
                }

                pedido.setStatus(StatusPedido.FECHADO_SEM_CREDITO);
            }

        } catch (Exception e) {
            if (uuidPagamento != null) {
                pagamentoGateway.estornar(pedido);
            }
            pedido.setStatus(StatusPedido.FECHADO_SEM_ESTOQUE);
        }

        pedidoGateway.salvar(pedido);
    }

    private BigDecimal calcularValorTotal(Pedido pedido, List<ProdutoDTO> produtos) {
        return pedido.getItens()
                .stream()
                .map(item -> {
                    ProdutoDTO produto = produtos.stream()
                            .filter(p -> p.sku()
                                    .equals(item.getSku()))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + item.getSku()));
                    return produto.preco()
                            .multiply(BigDecimal.valueOf(item.getQuantidade()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}