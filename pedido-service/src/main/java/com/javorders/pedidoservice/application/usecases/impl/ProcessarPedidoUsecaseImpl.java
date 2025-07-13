package com.javorders.pedidoservice.application.usecases.impl;

import com.javorders.pedidoservice.application.usecases.ProcessarPedidoUsecase;
import com.javorders.pedidoservice.domain.gateways.*;
import com.javorders.pedidoservice.domain.model.Pedido;
import com.javorders.pedidoservice.domain.model.StatusPedido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        try {
            var cliente = clienteGateway.buscarPorId(pedido.getClienteId());

            if (!cliente.temCreditoPara(pedido)) {
                pedido.setStatus(StatusPedido.FECHADO_SEM_CREDITO);
                pedidoGateway.salvar(pedido);
                return;
            }

            var produtos = produtoGateway.obterPorSkus(pedido.getItens());

            if (!pedido.temEstoqueDisponivel(produtos)) {
                pedido.setStatus(StatusPedido.FECHADO_SEM_ESTOQUE);
                pedidoGateway.salvar(pedido);
                return;
            }

            estoqueGateway.baixarEstoque(pedido);
            pagamentoGateway.solicitarPagamento(pedido);

            pedido.setStatus(StatusPedido.FECHADO_COM_SUCESSO);
            pedidoGateway.salvar(pedido);

        } catch (Exception e) {
            pedido.setStatus(StatusPedido.FECHADO_SEM_CREDITO); // fallback
            pedidoGateway.salvar(pedido);
        }
    }

}
