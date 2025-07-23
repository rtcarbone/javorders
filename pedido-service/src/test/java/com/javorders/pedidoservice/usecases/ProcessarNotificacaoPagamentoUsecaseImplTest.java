package com.javorders.pedidoservice.usecases;

import com.javorders.pedidoservice.application.usecases.impl.ProcessarNotificacaoPagamentoUsecaseImpl;
import com.javorders.pedidoservice.domain.gateways.EstoqueGateway;
import com.javorders.pedidoservice.domain.gateways.PagamentoGateway;
import com.javorders.pedidoservice.domain.gateways.PedidoGateway;
import com.javorders.pedidoservice.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

class ProcessarNotificacaoPagamentoUsecaseImplTest {

    private PedidoGateway pedidoGateway;
    private EstoqueGateway estoqueGateway;
    private PagamentoGateway pagamentoGateway;
    private ProcessarNotificacaoPagamentoUsecaseImpl usecase;

    @BeforeEach
    void setUp() {
        pedidoGateway = mock(PedidoGateway.class);
        estoqueGateway = mock(EstoqueGateway.class);
        pagamentoGateway = mock(PagamentoGateway.class);
        usecase = new ProcessarNotificacaoPagamentoUsecaseImpl(pedidoGateway, estoqueGateway, pagamentoGateway);
    }

    @Test
    void deveFecharPedidoComSucessoQuandoPagamentoAprovado() {
        UUID uuid = UUID.randomUUID();

        Pagamento pagamento = new Pagamento(uuid, StatusPagamento.APROVADO);
        Pedido pedido = Pedido.builder()
                .uuidTransacao(uuid)
                .status(StatusPedido.ABERTO)
                .itens(List.of(new ItemPedido("SKU123", 2)))
                .build();

        when(pagamentoGateway.buscarPorUuid(uuid)).thenReturn(Optional.of(pagamento));
        when(pedidoGateway.buscarPorUuidTransacao(uuid)).thenReturn(Optional.of(pedido));

        usecase.executar(uuid);

        verify(pedidoGateway).salvar(argThat(p ->
                                                     p.getStatus() == StatusPedido.FECHADO_COM_SUCESSO
        ));
        verifyNoInteractions(estoqueGateway);
    }

    @Test
    void deveReporEstoqueQuandoPagamentoRecusado() {
        UUID uuid = UUID.randomUUID();

        Pagamento pagamento = new Pagamento(uuid, StatusPagamento.RECUSADO);
        ItemPedido item = new ItemPedido("SKU456", 3);
        Pedido pedido = Pedido.builder()
                .uuidTransacao(uuid)
                .status(StatusPedido.ABERTO)
                .itens(List.of(item))
                .build();

        when(pagamentoGateway.buscarPorUuid(uuid)).thenReturn(Optional.of(pagamento));
        when(pedidoGateway.buscarPorUuidTransacao(uuid)).thenReturn(Optional.of(pedido));

        usecase.executar(uuid);

        verify(estoqueGateway).reporEstoque("SKU456", 3);
        verify(pedidoGateway).salvar(argThat(p ->
                                                     p.getStatus() == StatusPedido.FECHADO_SEM_CREDITO
        ));
    }

    @Test
    void deveLancarExcecaoQuandoPagamentoNaoEncontrado() {
        UUID uuid = UUID.randomUUID();

        when(pagamentoGateway.buscarPorUuid(uuid)).thenReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            usecase.executar(uuid);
        });

        verifyNoInteractions(pedidoGateway);
        verifyNoInteractions(estoqueGateway);
    }

    @Test
    void deveLancarExcecaoQuandoPedidoNaoEncontrado() {
        UUID uuid = UUID.randomUUID();

        when(pagamentoGateway.buscarPorUuid(uuid)).thenReturn(Optional.of(new Pagamento(uuid, StatusPagamento.APROVADO)));
        when(pedidoGateway.buscarPorUuidTransacao(uuid)).thenReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            usecase.executar(uuid);
        });

        verifyNoInteractions(estoqueGateway);
        verify(pagamentoGateway).buscarPorUuid(uuid);
    }
}