package com.javorders.pedidoservice.usecases;

import com.javorders.pedidoservice.application.usecases.impl.AlterarPedidoUsecaseImpl;
import com.javorders.pedidoservice.domain.gateways.PedidoGateway;
import com.javorders.pedidoservice.domain.model.ItemPedido;
import com.javorders.pedidoservice.domain.model.Pedido;
import com.javorders.pedidoservice.domain.model.StatusPedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AlterarPedidoUsecaseImplTest {

    private PedidoGateway pedidoGateway;
    private AlterarPedidoUsecaseImpl usecase;

    @BeforeEach
    void setUp() {
        pedidoGateway = mock(PedidoGateway.class);
        usecase = new AlterarPedidoUsecaseImpl(pedidoGateway);
    }

    @Test
    void deveAlterarPedidoComSucesso() {
        // Arrange
        Long pedidoId = 1L;
        Pedido pedido = Pedido.builder()
                .clienteId(10L)
                .numeroCartao("1234567890123456")
                .valorTotal(new BigDecimal("150.00"))
                .status(StatusPedido.ABERTO)
                .uuidTransacao(UUID.randomUUID())
                .itens(List.of(new ItemPedido("SKU123", 3)))
                .build();

        Pedido pedidoComId = pedido.toBuilder()
                .id(pedidoId)
                .build();

        when(pedidoGateway.salvar(pedidoComId)).thenReturn(pedidoComId);

        // Act
        Pedido resultado = usecase.executar(pedidoId, pedido);

        // Assert
        assertEquals(pedidoId, resultado.getId());
        assertEquals(pedido.getClienteId(), resultado.getClienteId());
        verify(pedidoGateway, times(1)).salvar(pedidoComId);
    }
}
