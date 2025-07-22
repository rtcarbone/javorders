package com.javorders.clienteservice.usecases;

import com.javorders.clienteservice.application.usecases.impl.BuscarClientePorIdUsecaseImpl;
import com.javorders.clienteservice.domain.gateways.ClienteGateway;
import com.javorders.clienteservice.domain.model.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuscarClientePorIdUsecaseImplTest {

    private ClienteGateway clienteGateway;
    private BuscarClientePorIdUsecaseImpl usecase;

    @BeforeEach
    void setUp() {
        clienteGateway = mock(ClienteGateway.class);
        usecase = new BuscarClientePorIdUsecaseImpl(clienteGateway);
    }

    @Test
    void deveRetornarClienteQuandoEncontrado() {
        // given
        Long id = 1L;
        Cliente cliente = new Cliente(id, "Raphael", "12345678901", LocalDate.of(1990, 1, 1), Collections.emptyList());
        when(clienteGateway.buscarPorId(id)).thenReturn(Optional.of(cliente));

        // when
        Optional<Cliente> resultado = usecase.executar(id);

        // then
        assertTrue(resultado.isPresent());
        assertEquals("Raphael", resultado.get()
                .getNome());
        verify(clienteGateway).buscarPorId(id);
    }

    @Test
    void deveRetornarVazioQuandoClienteNaoEncontrado() {
        // given
        Long id = 2L;
        when(clienteGateway.buscarPorId(id)).thenReturn(Optional.empty());

        // when
        Optional<Cliente> resultado = usecase.executar(id);

        // then
        assertFalse(resultado.isPresent());
        verify(clienteGateway).buscarPorId(id);
    }
}