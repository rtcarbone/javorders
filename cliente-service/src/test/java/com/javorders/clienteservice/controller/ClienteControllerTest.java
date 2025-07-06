package com.javorders.clienteservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javorders.clienteservice.domain.model.Cliente;
import com.javorders.clienteservice.domain.model.Endereco;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        Endereco endereco = Endereco.builder()
                .rua("Rua A")
                .numero("123")
                .cep("00000-000")
                .build();

        cliente = Cliente.builder()
                .nome("João da Silva")
                .cpf("12345678900")
                .dataNascimento(LocalDate.of(1990, 1, 1))
                .enderecos(List.of(endereco))
                .build();
    }

    @Test
    void deveCadastrarClienteComSucesso() throws Exception {
        mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpf").value("12345678900"));
    }
}