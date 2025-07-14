package com.javorders.pedidoservice.application.usecases.impl;

import com.javorders.pedidoservice.application.usecases.ProcessarPedidoUsecase;
import com.javorders.pedidoservice.domain.gateways.*;
import com.javorders.pedidoservice.domain.model.Pedido;
import com.javorders.pedidoservice.domain.model.StatusPedido;
import com.javorders.pedidoservice.infrastructure.dto.ClienteDTO;
import com.javorders.pedidoservice.infrastructure.dto.ProdutoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

        // Validar cliente
        ClienteDTO cliente = clienteGateway.buscarPorId(pedido.getClienteId());
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado para o ID: " + pedido.getClienteId());
        }

        pedido.setStatus(StatusPedido.ABERTO);

        List<ProdutoDTO> produtos = produtoGateway.obterPorSkus(pedido.getItens());

        // Calcular valor total
        BigDecimal valorTotal = pedido.calcularValorTotal(produtos);
        pedido.setValorTotal(valorTotal);

        try {
            // Criar ordem de pagamento
            UUID uuid = pagamentoGateway.solicitarPagamento(pedido);
            pedido.setUuidTransacao(uuid.toString());

            try {
                // Baixar estoque
                estoqueGateway.baixarEstoque(pedido);
                pedido.setStatus(StatusPedido.FECHADO_COM_SUCESSO);

            } catch (Exception e) {
                // Falha no estoque: estorna pagamento
                pagamentoGateway.estornar(pedido);
                pedido.setStatus(StatusPedido.FECHADO_SEM_ESTOQUE);
            }

        } catch (Exception e) {
            // Falha no pagamento: repor estoque
            estoqueGateway.reporEstoque(pedido);
            pedido.setStatus(StatusPedido.FECHADO_SEM_CREDITO);
        }

        pedidoGateway.salvar(pedido);
    }

}