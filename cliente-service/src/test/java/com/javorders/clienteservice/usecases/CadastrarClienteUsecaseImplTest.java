package com.javorders.clienteservice.usecases;

import com.javorders.clienteservice.application.usecases.impl.CadastrarClienteUsecaseImpl;
import com.javorders.clienteservice.domain.gateways.ClienteGateway;
import com.javorders.clienteservice.domain.model.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CadastrarClienteUsecaseImplTest {

    private ClienteGateway gateway;
    private CadastrarClienteUsecaseImpl usecase;

    @BeforeEach
    void setUp() {
        gateway = mock(ClienteGateway.class);
        usecase = new CadastrarClienteUsecaseImpl(gateway);
    }

    @Test
    void deveCadastrarClienteComCpfNovo() {
        // given
        Cliente cliente = new Cliente(1L, "Raphael", "12345678900", LocalDate.of(1990, 1, 1), Collections.emptyList());

        when(gateway.buscarPorCpf(cliente.getCpf())).thenReturn(Optional.empty());
        when(gateway.salvar(cliente)).thenReturn(cliente);

        // when
        Cliente resultado = usecase.executar(cliente);

        // then
        assertNotNull(resultado);
        assertEquals(cliente, resultado);
        verify(gateway).salvar(cliente);
    }

    @Test
    void deveLancarExcecaoQuandoCpfJaCadastrado() {
        // given
        Cliente cliente = new Cliente(1L, "Raphael", "12345678900", LocalDate.of(1990, 1, 1), Collections.emptyList());

        when(gateway.buscarPorCpf(cliente.getCpf())).thenReturn(Optional.of(cliente));

        // when + then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usecase.executar(cliente);
        });

        assertEquals("CPF já cadastrado.", exception.getMessage());
        verify(gateway, never()).salvar(any());
    }
}