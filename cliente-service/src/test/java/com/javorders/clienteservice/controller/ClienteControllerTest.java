package com.javorders.clienteservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javorders.clienteservice.application.usecases.*;
import com.javorders.clienteservice.domain.model.Cliente;
import com.javorders.clienteservice.infrastructure.controller.ClienteController;
import com.javorders.clienteservice.infrastructure.dto.ClienteRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CadastrarClienteUsecase cadastrarClienteUsecase;
    @MockBean
    private AlterarClienteUsecase alterarClienteUsecase;
    @MockBean
    private DeletarClienteUsecase deletarClienteUsecase;
    @MockBean
    private BuscarClientePorIdUsecase buscarClientePorIdUsecase;
    @MockBean
    private ConsultarClientesUsecase consultarClientesUsecase;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente(1L, "Raphael", "12345678901", LocalDate.of(1990, 1, 1), Collections.emptyList());
    }

    @Test
    void deveCadastrarCliente() throws Exception {
        ClienteRequestDTO request = new ClienteRequestDTO("Raphael", "12345678901", LocalDate.of(1990, 1, 1), Collections.emptyList());
        when(cadastrarClienteUsecase.executar(any())).thenReturn(cliente);

        mockMvc.perform(post("/clientes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cliente.getId()))
                .andExpect(jsonPath("$.nome").value(cliente.getNome()));
    }

    @Test
    void deveAlterarCliente() throws Exception {
        ClienteRequestDTO request = new ClienteRequestDTO("Raphael", "12345678901", LocalDate.of(1990, 1, 1), Collections.emptyList());
        when(alterarClienteUsecase.executar(Mockito.eq(1L), any())).thenReturn(cliente);

        mockMvc.perform(put("/clientes/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cliente.getId()))
                .andExpect(jsonPath("$.nome").value(cliente.getNome()));
    }

    @Test
    void deveDeletarCliente() throws Exception {
        mockMvc.perform(delete("/clientes/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deveBuscarClientePorId() throws Exception {
        when(buscarClientePorIdUsecase.executar(1L)).thenReturn(Optional.of(cliente));

        mockMvc.perform(get("/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cliente.getId()))
                .andExpect(jsonPath("$.nome").value(cliente.getNome()));
    }

    @Test
    void deveRetornarNotFoundQuandoClienteNaoExiste() throws Exception {
        when(buscarClientePorIdUsecase.executar(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/clientes/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveListarTodosOsClientes() throws Exception {
        List<Cliente> lista = List.of(
                cliente,
                new Cliente(2L, "Amanda", "98765432100", LocalDate.of(1985, 5, 15), Collections.emptyList())
        );
        when(consultarClientesUsecase.executar()).thenReturn(lista);

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome", is("Raphael")))
                .andExpect(jsonPath("$[1].nome", is("Amanda")));
    }
}