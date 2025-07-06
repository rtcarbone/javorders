package com.javorders.estoqueservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javorders.estoqueservice.domain.model.Estoque;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EstoqueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCadastrarEstoqueComSucesso() throws Exception {
        Estoque estoque = Estoque.builder()
                .sku("SKU999")
                .quantidade(10)
                .build();

        mockMvc.perform(post("/estoques")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(estoque)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sku").value("SKU999"));
    }
}