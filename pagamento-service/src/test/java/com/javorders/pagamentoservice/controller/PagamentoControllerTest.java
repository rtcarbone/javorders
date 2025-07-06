package com.javorders.pagamentoservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javorders.pagamentoservice.domain.model.Pagamento;
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
public class PagamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveEfetuarPagamentoComSucesso() throws Exception {
        Pagamento pagamento = Pagamento.builder()
                .clienteId(1L)
                .valor(BigDecimal.valueOf(200.00))
                .build();

        mockMvc.perform(post("/pagamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pagamento)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clienteId").value(1));
    }
}