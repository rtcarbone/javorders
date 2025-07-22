package com.javorders.clienteservice.gateway;

import com.javorders.clienteservice.domain.model.Cliente;
import com.javorders.clienteservice.infrastructure.gateway.ClienteGatewayImpl;
import com.javorders.clienteservice.infrastructure.persistence.entity.ClienteEntity;
import com.javorders.clienteservice.infrastructure.persistence.mapper.ClienteMapper;
import com.javorders.clienteservice.infrastructure.persistence.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteGatewayImplTest {

    private ClienteRepository repository;
    private ClienteGatewayImpl gateway;

    private Cliente cliente;
    private ClienteEntity clienteEntity;

    @BeforeEach
    void setUp() {
        repository = mock(ClienteRepository.class);
        gateway = new ClienteGatewayImpl(repository);

        cliente = Cliente.builder()
                .id(1L)
                .nome("Raphael Carbone")
                .cpf("12345678900")
                .dataNascimento(LocalDate.of(1990, 1, 1))
                .enderecos(List.of())
                .build();

        clienteEntity = ClienteMapper.toEntity(cliente);
    }

    @Test
    void deveSalvarCliente() {
        when(repository.save(any())).thenReturn(clienteEntity);

        Cliente salvo = gateway.salvar(cliente);

        assertNotNull(salvo);
        assertEquals(cliente.getNome(), salvo.getNome());
        verify(repository).save(any());
    }

    @Test
    void deveBuscarPorCpf() {
        when(repository.findByCpf("12345678900")).thenReturn(Optional.of(clienteEntity));

        Optional<Cliente> encontrado = gateway.buscarPorCpf("12345678900");

        assertTrue(encontrado.isPresent());
        assertEquals("12345678900", encontrado.get()
                .getCpf());
    }

    @Test
    void deveBuscarPorId() {
        when(repository.findById(1L)).thenReturn(Optional.of(clienteEntity));

        Optional<Cliente> encontrado = gateway.buscarPorId(1L);

        assertTrue(encontrado.isPresent());
        assertEquals(1L, encontrado.get()
                .getId());
    }

    @Test
    void deveListarTodos() {
        when(repository.findAll()).thenReturn(List.of(clienteEntity));

        List<Cliente> clientes = gateway.listarTodos();

        assertEquals(1, clientes.size());
        assertEquals("Raphael Carbone", clientes.get(0)
                .getNome());
    }

    @Test
    void deveDeletarPorId() {
        gateway.deletarPorId(1L);

        verify(repository).deleteById(1L);
    }
}
