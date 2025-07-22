package com.javorders.clienteservice.usecases;

import com.javorders.clienteservice.application.usecases.impl.ConsultarClientesUsecaseImpl;
import com.javorders.clienteservice.domain.gateways.ClienteGateway;
import com.javorders.clienteservice.domain.model.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ConsultarClientesUsecaseImplTest {

    private ClienteGateway clienteGateway;
    private ConsultarClientesUsecaseImpl usecase;

    @BeforeEach
    void setUp() {
        clienteGateway = mock(ClienteGateway.class);
        usecase = new ConsultarClientesUsecaseImpl(clienteGateway);
    }

    @Test
    void deveRetornarListaDeClientes() {
        // given
        List<Cliente> clientes = List.of(
                new Cliente(1L, "Raphael", "12345678901", LocalDate.of(1990, 1, 1), Collections.emptyList()),
                new Cliente(2L, "Carla", "98765432100", LocalDate.of(1985, 5, 20), Collections.emptyList())
        );
        when(clienteGateway.listarTodos()).thenReturn(clientes);

        // when
        List<Cliente> resultado = usecase.executar();

        // then
        assertEquals(2, resultado.size());
        assertEquals("Raphael", resultado.get(0)
                .getNome());
        assertEquals("Carla", resultado.get(1)
                .getNome());
        verify(clienteGateway).listarTodos();
    }
}