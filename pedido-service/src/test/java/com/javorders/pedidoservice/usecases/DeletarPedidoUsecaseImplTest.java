package com.javorders.pedidoservice.usecases;

import com.javorders.pedidoservice.application.usecases.impl.DeletarPedidoUsecaseImpl;
import com.javorders.pedidoservice.domain.gateways.PedidoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class DeletarPedidoUsecaseImplTest {

    private PedidoGateway pedidoGateway;
    private DeletarPedidoUsecaseImpl usecase;

    @BeforeEach
    void setUp() {
        pedidoGateway = mock(PedidoGateway.class);
        usecase = new DeletarPedidoUsecaseImpl(pedidoGateway);
    }

    @Test
    void deveDeletarPedidoPorId() {
        // Arrange
        Long id = 1L;

        // Act
        usecase.executar(id);

        // Assert
        verify(pedidoGateway, times(1)).deletarPorId(id);
    }
}
