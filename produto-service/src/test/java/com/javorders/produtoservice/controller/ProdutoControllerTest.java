package com.javorders.produtoservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javorders.produtoservice.application.usecases.*;
import com.javorders.produtoservice.domain.model.Produto;
import com.javorders.produtoservice.infrastructure.controller.ProdutoController;
import com.javorders.produtoservice.infrastructure.dto.ProdutoRequestDTO;
import com.javorders.produtoservice.infrastructure.mapper.ProdutoResponseMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ProdutoController.class)
class ProdutoControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CadastrarProdutoUsecase cadastrarProdutoUsecase;
    @MockBean
    private AlterarProdutoUsecase alterarProdutoUsecase;
    @MockBean
    private DeletarProdutoUsecase deletarProdutoUsecase;
    @MockBean
    private ConsultarProdutosPorSkuUsecase consultarProdutosPorSkuUsecase;
    @MockBean
    private ConsultarProdutosUsecase consultarProdutosUsecase;

    @Test
    void deveCadastrarProduto() throws Exception {
        var dto = new ProdutoRequestDTO("Produto X", "sku-x", new BigDecimal("123.45"));

        var produto = new Produto(1L, "Produto X", "sku-x", new BigDecimal("123.45"));
        var response = ProdutoResponseMapper.toResponse(produto);

        Mockito.when(cadastrarProdutoUsecase.executar(Mockito.any()))
                .thenReturn(produto);

        mockMvc.perform(post("/produtos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.nome").value(response.nome()))
                .andExpect(jsonPath("$.sku").value(response.sku()));
    }

    @Test
    void deveAlterarProduto() throws Exception {
        var dto = new ProdutoRequestDTO("Produto Alterado", "sku-alt", new BigDecimal("250.00"));

        var produto = new Produto(1L, "Produto Alterado", "sku-alt", new BigDecimal("250.00"));
        var response = ProdutoResponseMapper.toResponse(produto);

        Mockito.when(alterarProdutoUsecase.executar(Mockito.eq(1L), Mockito.any()))
                .thenReturn(produto);

        mockMvc.perform(put("/produtos/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(response.nome()))
                .andExpect(jsonPath("$.sku").value(response.sku()));
    }

    @Test
    void deveDeletarProduto() throws Exception {
        mockMvc.perform(delete("/produtos/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(deletarProdutoUsecase)
                .executar(1L);
    }

    @Test
    void deveBuscarProdutosPorSku() throws Exception {
        var produto = new Produto(1L, "Produto A", "sku-a", new BigDecimal("100"));
        Mockito.when(consultarProdutosPorSkuUsecase.executar(List.of("sku-a")))
                .thenReturn(List.of(produto));

        mockMvc.perform(post("/produtos/por-skus")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(List.of("sku-a"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].sku").value("sku-a"));
    }

    @Test
    void deveListarProdutos() throws Exception {
        var produto = new Produto(1L, "Produto A", "sku-a", new BigDecimal("100"));
        Mockito.when(consultarProdutosUsecase.executar())
                .thenReturn(List.of(produto));

        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Produto A"));
    }
}