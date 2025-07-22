package com.javorders.estoqueservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javorders.estoqueservice.application.usecases.BaixarEstoqueUsecase;
import com.javorders.estoqueservice.application.usecases.CadastrarEstoqueUsecase;
import com.javorders.estoqueservice.application.usecases.ConsultarEstoqueUsecase;
import com.javorders.estoqueservice.application.usecases.RestaurarEstoqueUsecase;
import com.javorders.estoqueservice.domain.model.Estoque;
import com.javorders.estoqueservice.infrastructure.controller.EstoqueController;
import com.javorders.estoqueservice.infrastructure.dto.EstoqueRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EstoqueController.class)
class EstoqueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CadastrarEstoqueUsecase cadastrarEstoqueUsecase;

    @MockBean
    private ConsultarEstoqueUsecase consultarEstoqueUsecase;

    @MockBean
    private BaixarEstoqueUsecase baixarEstoqueUsecase;

    @MockBean
    private RestaurarEstoqueUsecase restaurarEstoqueUsecase;

    @Autowired
    private ObjectMapper objectMapper;

    private Estoque estoque;

    @BeforeEach
    void setup() {
        estoque = Estoque.builder()
                .id(1L)
                .sku("ABC-123")
                .quantidade(50)
                .build();
    }

    @Test
    void deveCadastrarEstoque() throws Exception {
        EstoqueRequestDTO dto = new EstoqueRequestDTO("ABC-123", 50);

        Mockito.when(cadastrarEstoqueUsecase.executar(any()))
                .thenReturn(estoque);

        mockMvc.perform(post("/estoques")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sku").value("ABC-123"))
                .andExpect(jsonPath("$.quantidade").value(50));
    }

    @Test
    void deveConsultarEstoquePorSku() throws Exception {
        Mockito.when(consultarEstoqueUsecase.executar("ABC-123"))
                .thenReturn(Optional.of(estoque));

        mockMvc.perform(get("/estoques/ABC-123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sku").value("ABC-123"));
    }

    @Test
    void deveRetornarNotFoundSeEstoqueNaoEncontrado() throws Exception {
        Mockito.when(consultarEstoqueUsecase.executar("XYZ-999"))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/estoques/XYZ-999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveBaixarEstoque() throws Exception {
        EstoqueRequestDTO dto = new EstoqueRequestDTO("ABC-123", 10);

        mockMvc.perform(patch("/estoques/baixar")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNoContent());

        Mockito.verify(baixarEstoqueUsecase)
                .executar("ABC-123", 10);
    }

    @Test
    void deveRestaurarEstoque() throws Exception {
        EstoqueRequestDTO dto = new EstoqueRequestDTO("ABC-123", 5);

        mockMvc.perform(patch("/estoques/restaurar")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNoContent());

        Mockito.verify(restaurarEstoqueUsecase)
                .executar("ABC-123", 5);
    }
}
