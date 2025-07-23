package com.javorders.pedidoservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javorders.pedidoservice.application.usecases.AlterarPedidoUsecase;
import com.javorders.pedidoservice.application.usecases.ConsultarPedidoUsecase;
import com.javorders.pedidoservice.application.usecases.DeletarPedidoUsecase;
import com.javorders.pedidoservice.application.usecases.ListarPedidosUsecase;
import com.javorders.pedidoservice.domain.model.ItemPedido;
import com.javorders.pedidoservice.domain.model.Pedido;
import com.javorders.pedidoservice.domain.model.StatusPedido;
import com.javorders.pedidoservice.infrastructure.controller.PedidoController;
import com.javorders.pedidoservice.infrastructure.dto.ItemPedidoDTO;
import com.javorders.pedidoservice.infrastructure.dto.PedidoRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PedidoController.class)
class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlterarPedidoUsecase alterarPedidoUsecase;

    @MockBean
    private ConsultarPedidoUsecase consultarPedidoUsecase;

    @MockBean
    private ListarPedidosUsecase listarPedidosUsecase;

    @MockBean
    private DeletarPedidoUsecase deletarPedidoUsecase;

    @Autowired
    private ObjectMapper objectMapper;

    private Pedido pedido;

    @BeforeEach
    void setup() {
        pedido = Pedido.builder()
                .id(1L)
                .clienteId(1L)
                .numeroCartao("1234567890123456")
                .status(StatusPedido.ABERTO)
                .valorTotal(new BigDecimal("100.00"))
                .uuidTransacao(UUID.randomUUID())
                .itens(List.of(new ItemPedido("SKU-1", 2)))
                .build();
    }

    @Test
    void deveAlterarPedido() throws Exception {
        PedidoRequestDTO dto = new PedidoRequestDTO(1L, "1234567890123456", List.of(new ItemPedidoDTO("SKU-1", 2)));

        Mockito.when(alterarPedidoUsecase.executar(anyLong(), any()))
                .thenReturn(pedido);

        mockMvc.perform(put("/pedidos/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clienteId").value(1))
                .andExpect(jsonPath("$.itens[0].sku").value("SKU-1"));
    }

    @Test
    void deveConsultarPedidoPorId() throws Exception {
        Mockito.when(consultarPedidoUsecase.executar(1L))
                .thenReturn(Optional.of(pedido));

        mockMvc.perform(get("/pedidos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clienteId").value(1))
                .andExpect(jsonPath("$.status").value("ABERTO"));
    }

    @Test
    void deveRetornarNotFoundSePedidoNaoEncontrado() throws Exception {
        Mockito.when(consultarPedidoUsecase.executar(99L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/pedidos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveListarTodosOsPedidos() throws Exception {
        Mockito.when(listarPedidosUsecase.executar())
                .thenReturn(List.of(pedido));

        mockMvc.perform(get("/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].clienteId").value(1));
    }

    @Test
    void deveDeletarPedido() throws Exception {
        mockMvc.perform(delete("/pedidos/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(deletarPedidoUsecase)
                .executar(1L);
    }
}