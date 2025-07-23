package com.javorders.pedidoservice.usecases;

import com.javorders.pedidoservice.application.usecases.impl.ListarPedidosUsecaseImpl;
import com.javorders.pedidoservice.domain.gateways.PedidoGateway;
import com.javorders.pedidoservice.domain.model.Pedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ListarPedidosUsecaseImplTest {

    private PedidoGateway pedidoGateway;
    private ListarPedidosUsecaseImpl usecase;

    @BeforeEach
    void setUp() {
        pedidoGateway = mock(PedidoGateway.class);
        usecase = new ListarPedidosUsecaseImpl(pedidoGateway);
    }

    @Test
    void deveListarTodosOsPedidos() {
        // Arrange
        List<Pedido> pedidosMock = List.of(
                Pedido.builder()
                        .id(1L)
                        .build(),
                Pedido.builder()
                        .id(2L)
                        .build()
        );
        when(pedidoGateway.buscarTodos()).thenReturn(pedidosMock);

        // Act
        List<Pedido> resultado = usecase.executar();

        // Assert
        assertThat(resultado).hasSize(2);
        assertThat(resultado).isEqualTo(pedidosMock);
        verify(pedidoGateway, times(1)).buscarTodos();
    }
}