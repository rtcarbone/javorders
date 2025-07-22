package com.javorders.clienteservice.usecases;

import com.javorders.clienteservice.application.usecases.impl.AlterarClienteUsecaseImpl;
import com.javorders.clienteservice.domain.gateways.ClienteGateway;
import com.javorders.clienteservice.domain.model.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AlterarClienteUsecaseImplTest {

    private ClienteGateway gateway;
    private AlterarClienteUsecaseImpl usecase;

    @BeforeEach
    void setUp() {
        gateway = mock(ClienteGateway.class);
        usecase = new AlterarClienteUsecaseImpl(gateway);
    }

    @Test
    void deveAlterarClienteComSucesso() {
        // given
        Long id = 1L;
        Cliente existente = new Cliente(id, "João", "11111111111", LocalDate.of(1990, 1, 1), Collections.emptyList());
        Cliente atualizado = new Cliente(null, "Raphael", "11111111111", LocalDate.of(1995, 5, 5), Collections.emptyList());
        Cliente resultado = existente.toBuilder()
                .nome("Raphael")
                .dataNascimento(LocalDate.of(1995, 5, 5))
                .build();

        when(gateway.buscarPorId(id)).thenReturn(Optional.of(existente));
        when(gateway.buscarPorCpf("11111111111")).thenReturn(Optional.of(existente));
        when(gateway.salvar(any())).thenReturn(resultado);

        // when
        Cliente alterado = usecase.executar(id, atualizado);

        // then
        assertNotNull(alterado);
        assertEquals("Raphael", alterado.getNome());
        verify(gateway).salvar(any(Cliente.class));
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoEncontrado() {
        // given
        Long id = 1L;
        Cliente atualizado = new Cliente(null, "Novo Nome", "11111111111", LocalDate.of(1995, 5, 5), Collections.emptyList());

        when(gateway.buscarPorId(id)).thenReturn(Optional.empty());

        // when + then
        var ex = assertThrows(IllegalArgumentException.class, () -> usecase.executar(id, atualizado));
        assertEquals("Cliente não encontrado.", ex.getMessage());
        verify(gateway, never()).salvar(any());
    }

    @Test
    void deveLancarExcecaoQuandoCpfJaCadastradoPorOutroCliente() {
        // given
        Long id = 1L;
        Cliente existente = new Cliente(id, "João", "11111111111", LocalDate.of(1990, 1, 1), Collections.emptyList());
        Cliente outroCliente = new Cliente(2L, "Outro", "99999999999", LocalDate.of(1980, 1, 1), Collections.emptyList());
        Cliente atualizado = new Cliente(null, "Raphael", "99999999999", LocalDate.of(1995, 5, 5), Collections.emptyList());

        when(gateway.buscarPorId(id)).thenReturn(Optional.of(existente));
        when(gateway.buscarPorCpf("99999999999")).thenReturn(Optional.of(outroCliente));

        // when + then
        var ex = assertThrows(IllegalArgumentException.class, () -> usecase.executar(id, atualizado));
        assertEquals("CPF já cadastrado por outro cliente.", ex.getMessage());
        verify(gateway, never()).salvar(any());
    }
}
