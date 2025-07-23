package com.javorders.pedidoservice.usecases;

import com.javorders.pedidoservice.application.usecases.impl.ProcessarPedidoUsecaseImpl;
import com.javorders.pedidoservice.domain.gateways.*;
import com.javorders.pedidoservice.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ProcessarPedidoUsecaseImplTest {

    private ClienteGateway clienteGateway;
    private ProdutoGateway produtoGateway;
    private EstoqueGateway estoqueGateway;
    private PagamentoGateway pagamentoGateway;
    private PedidoGateway pedidoGateway;
    private ProcessarPedidoUsecaseImpl usecase;

    @BeforeEach
    void setup() {
        clienteGateway = mock(ClienteGateway.class);
        produtoGateway = mock(ProdutoGateway.class);
        estoqueGateway = mock(EstoqueGateway.class);
        pagamentoGateway = mock(PagamentoGateway.class);
        pedidoGateway = mock(PedidoGateway.class);

        usecase = new ProcessarPedidoUsecaseImpl(
                clienteGateway, produtoGateway, estoqueGateway, pagamentoGateway, pedidoGateway
        );
    }

    @Test
    void deveLancarExcecaoSeClienteNaoExiste() {
        Pedido pedido = Pedido.builder()
                .clienteId(1L)
                .build();

        when(clienteGateway.buscarPorId(1L)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> usecase.executar(pedido));

        verifyNoInteractions(produtoGateway, estoqueGateway, pagamentoGateway);
        verify(pedidoGateway, never()).salvar(any());
    }

    @Test
    void deveLancarExcecaoSeProdutosNaoEncontrados() {
        Pedido pedido = Pedido.builder()
                .clienteId(1L)
                .itens(List.of(new ItemPedido("SKU1", 1)))
                .build();

        when(clienteGateway.buscarPorId(1L)).thenReturn(new Cliente(1L, "João", "123"));
        when(produtoGateway.obterPorSkus(any())).thenReturn(List.of());

        assertThrows(IllegalArgumentException.class, () -> usecase.executar(pedido));

        verify(pedidoGateway, never()).salvar(any());
    }

    @Test
    void deveProcessarPedidoComSucesso() {
        Pedido pedido = Pedido.builder()
                .clienteId(1L)
                .numeroCartao("123")
                .itens(List.of(new ItemPedido("SKU1", 2)))
                .build();

        Produto produto = new Produto("SKU1", BigDecimal.TEN);

        when(clienteGateway.buscarPorId(1L)).thenReturn(new Cliente(1L, "João", "123"));
        when(produtoGateway.obterPorSkus(any())).thenReturn(List.of(produto));
        doNothing().when(estoqueGateway)
                .baixarEstoque("SKU1", 2);
        when(pagamentoGateway.solicitarPagamento(any()))
                .thenReturn(new Pagamento(UUID.randomUUID(), null));

        usecase.executar(pedido);

        verify(estoqueGateway).baixarEstoque("SKU1", 2);
        verify(pagamentoGateway).solicitarPagamento(any());
        verify(pedidoGateway).salvar(any(Pedido.class));
    }

    @Test
    void deveFecharPedidoSemEstoqueSeBaixarFalhar() {
        Pedido pedido = Pedido.builder()
                .clienteId(1L)
                .numeroCartao("123")
                .itens(List.of(new ItemPedido("SKU1", 1)))
                .build();

        Produto produto = new Produto("SKU1", BigDecimal.TEN);

        when(clienteGateway.buscarPorId(1L)).thenReturn(new Cliente(1L, "João", "123"));
        when(produtoGateway.obterPorSkus(any())).thenReturn(List.of(produto));
        doThrow(new RuntimeException("Erro")).when(estoqueGateway)
                .baixarEstoque("SKU1", 1);

        usecase.executar(pedido);

        verify(pedidoGateway).salvar(argThat(p -> p.getStatus() == StatusPedido.FECHADO_SEM_ESTOQUE));
        verify(pagamentoGateway, never()).solicitarPagamento(any());
    }

    @Test
    void deveReporEstoqueSePagamentoFalhar() {
        Pedido pedido = Pedido.builder()
                .clienteId(1L)
                .numeroCartao("123")
                .itens(List.of(new ItemPedido("SKU1", 1)))
                .build();

        Produto produto = new Produto("SKU1", BigDecimal.TEN);

        when(clienteGateway.buscarPorId(1L)).thenReturn(new Cliente(1L, "João", "123"));
        when(produtoGateway.obterPorSkus(any())).thenReturn(List.of(produto));
        doNothing().when(estoqueGateway)
                .baixarEstoque("SKU1", 1);
        doThrow(new RuntimeException("Falha pagamento")).when(pagamentoGateway)
                .solicitarPagamento(any());

        usecase.executar(pedido);

        verify(estoqueGateway).reporEstoque("SKU1", 1);
        verify(pedidoGateway).salvar(any(Pedido.class));
    }
}
