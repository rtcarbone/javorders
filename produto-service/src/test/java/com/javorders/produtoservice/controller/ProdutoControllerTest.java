package com.javorders.produtoservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javorders.produtoservice.domain.model.Produto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCadastrarProdutoComSucesso() throws Exception {
        Produto produto = Produto.builder()
                .nome("Notebook")
                .sku("NBK123")
                .preco(BigDecimal.valueOf(3000.00))
                .build();

        mockMvc.perform(post("/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sku").value("NBK123"));
    }
}