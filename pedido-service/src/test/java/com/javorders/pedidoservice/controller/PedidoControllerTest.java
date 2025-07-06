package com.javorders.pedidoservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javorders.pedidoservice.domain.model.ItemPedido;
import com.javorders.pedidoservice.domain.model.Pedido;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarPedidoComSucesso() throws Exception {
        Pedido pedido = Pedido.builder()
                .clienteId(1L)
                .valorTotal(BigDecimal.valueOf(150.00))
                .itens(List.of(ItemPedido.builder().sku("SKU001").quantidade(2).build()))
                .build();

        mockMvc.perform(post("/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pedido)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clienteId").value(1))
                .andExpect(jsonPath("$.itens[0].sku").value("SKU001"));
    }
}