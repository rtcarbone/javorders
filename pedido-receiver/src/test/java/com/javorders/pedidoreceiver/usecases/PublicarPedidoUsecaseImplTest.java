package com.javorders.pedidoreceiver.usecases;

import com.javorders.pedidoreceiver.application.usecases.impl.PublicarPedidoUsecaseImpl;
import com.javorders.pedidoreceiver.domain.gateways.PedidoPublisherGateway;
import com.javorders.pedidoreceiver.domain.model.Pedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PublicarPedidoUsecaseImplTest {

    private PedidoPublisherGateway pedidoPublisherGateway;
    private PublicarPedidoUsecaseImpl usecase;

    @BeforeEach
    void setUp() {
        pedidoPublisherGateway = mock(PedidoPublisherGateway.class);
        usecase = new PublicarPedidoUsecaseImpl(pedidoPublisherGateway);
    }

    @Test
    void devePublicarPedido() {
        // given
        Pedido pedido = Pedido.builder()
                .id(1L)
                .clienteId(2L)
                .numeroCartao("1234567890123456")
                .build();

        // when
        usecase.executar(pedido);

        // then
        verify(pedidoPublisherGateway).publicar(pedido);
    }
}
