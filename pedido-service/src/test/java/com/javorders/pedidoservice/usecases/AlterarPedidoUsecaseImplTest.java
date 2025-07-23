package com.javorders.pedidoservice.usecases;

import com.javorders.pedidoservice.application.usecases.impl.AlterarPedidoUsecaseImpl;
import com.javorders.pedidoservice.domain.gateways.PedidoGateway;
import com.javorders.pedidoservice.domain.model.ItemPedido;
import com.javorders.pedidoservice.domain.model.Pedido;
import com.javorders.pedidoservice.domain.model.StatusPedido;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AlterarPedidoUsecaseImplTest {

    private final PedidoGateway gateway = mock(PedidoGateway.class);
    private final AlterarPedidoUsecaseImpl usecase = new AlterarPedidoUsecaseImpl(gateway);

    @Test
    void deveAlterarPedidoComSucesso() {
        // Arrange
        Long id = 1L;

        Pedido pedido = Pedido.builder()
                .clienteId(2L)
                .itens(List.of(new ItemPedido("ABC123", 3)))
                .numeroCartao("1234")
                .valorTotal(BigDecimal.valueOf(100))
                .status(StatusPedido.ABERTO)
                .build();

        Pedido esperado = pedido.toBuilder()
                .id(id)
                .build();

        when(gateway.salvar(any(Pedido.class))).thenReturn(esperado);

        // Act
        Pedido resultado = usecase.executar(id, pedido);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(id);
        assertThat(resultado.getClienteId()).isEqualTo(2L);

        verify(gateway, times(1)).salvar(any(Pedido.class));
    }
}
