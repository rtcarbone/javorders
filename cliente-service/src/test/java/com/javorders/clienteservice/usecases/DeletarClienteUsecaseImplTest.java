package com.javorders.clienteservice.usecases;

import com.javorders.clienteservice.application.usecases.impl.DeletarClienteUsecaseImpl;
import com.javorders.clienteservice.domain.gateways.ClienteGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DeletarClienteUsecaseImplTest {

    private ClienteGateway gateway;
    private DeletarClienteUsecaseImpl usecase;

    @BeforeEach
    void setUp() {
        gateway = mock(ClienteGateway.class);
        usecase = new DeletarClienteUsecaseImpl(gateway);
    }

    @Test
    void deveDeletarClientePorId() {
        // given
        Long id = 1L;

        // when
        usecase.executar(id);

        // then
        verify(gateway).deletarPorId(id);
    }
}
