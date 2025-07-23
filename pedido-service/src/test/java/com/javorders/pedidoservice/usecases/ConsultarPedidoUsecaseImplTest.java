package com.javorders.pedidoservice.usecases;

import com.javorders.pedidoservice.application.usecases.impl.ConsultarPedidoUsecaseImpl;
import com.javorders.pedidoservice.domain.gateways.PedidoGateway;
import com.javorders.pedidoservice.domain.model.ItemPedido;
import com.javorders.pedidoservice.domain.model.Pedido;
import com.javorders.pedidoservice.domain.model.StatusPedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConsultarPedidoUsecaseImplTest {

    private PedidoGateway pedidoGateway;
    private ConsultarPedidoUsecaseImpl usecase;

    @BeforeEach
    void setUp() {
        pedidoGateway = mock(PedidoGateway.class);
        usecase = new ConsultarPedidoUsecaseImpl(pedidoGateway);
    }

    @Test
    void deveRetornarPedidoQuandoEncontrado() {
        // Arrange
        Long id = 1L;
        Pedido pedido = Pedido.builder()
                .id(id)
                .clienteId(10L)
                .numeroCartao("1234567890123456")
                .valorTotal(new BigDecimal("200.00"))
                .status(StatusPedido.ABERTO)
                .uuidTransacao(UUID.randomUUID())
                .itens(List.of(new ItemPedido("SKU123", 2)))
                .build();

        when(pedidoGateway.buscarPorId(id)).thenReturn(Optional.of(pedido));

        // Act
        Optional<Pedido> resultado = usecase.executar(id);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(id, resultado.get()
                .getId());
        verify(pedidoGateway, times(1)).buscarPorId(id);
    }

    @Test
    void deveRetornarOptionalVazioQuandoPedidoNaoEncontrado() {
        // Arrange
        Long id = 999L;
        when(pedidoGateway.buscarPorId(id)).thenReturn(Optional.empty());

        // Act
        Optional<Pedido> resultado = usecase.executar(id);

        // Assert
        assertFalse(resultado.isPresent());
        verify(pedidoGateway, times(1)).buscarPorId(id);
    }
}
