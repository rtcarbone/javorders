package com.javorders.pedidoreceiver.controller;

import com.javorders.pedidoreceiver.application.usecases.PublicarPedidoUsecase;
import com.javorders.pedidoreceiver.domain.model.Pedido;
import com.javorders.pedidoreceiver.infrastructure.controller.PedidoController;
import com.javorders.pedidoreceiver.infrastructure.dto.PedidoDTO;
import com.javorders.pedidoreceiver.infrastructure.mapper.PedidoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PedidoControllerTest {

    private PublicarPedidoUsecase publicarPedidoUsecase;
    private PedidoMapper pedidoMapper;
    private PedidoController controller;

    @BeforeEach
    void setUp() {
        publicarPedidoUsecase = mock(PublicarPedidoUsecase.class);
        pedidoMapper = mock(PedidoMapper.class);
        controller = new PedidoController(publicarPedidoUsecase, pedidoMapper);
    }

    @Test
    void devePublicarPedidoComSucesso() {
        // given
        PedidoDTO dto = new PedidoDTO(
                1L,
                "1234567890123456",
                null
        );

        Pedido pedido = Pedido.builder()
                .id(1L)
                .clienteId(1L)
                .numeroCartao("1234567890123456")
                .build();

        when(pedidoMapper.toDomain(dto)).thenReturn(pedido);

        // when
        ResponseEntity<Void> response = controller.publicarPedido(dto);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        verify(pedidoMapper).toDomain(dto);
        verify(publicarPedidoUsecase).executar(pedido);
    }
}
