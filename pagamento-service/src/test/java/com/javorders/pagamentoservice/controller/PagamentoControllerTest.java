package com.javorders.pagamentoservice.controller;

import com.javorders.pagamentoservice.application.usecases.BuscarPagamentoPorUuidUsecase;
import com.javorders.pagamentoservice.application.usecases.EstornarPagamentoUsecase;
import com.javorders.pagamentoservice.application.usecases.SolicitarPagamentoUsecase;
import com.javorders.pagamentoservice.domain.model.Pagamento;
import com.javorders.pagamentoservice.domain.model.StatusPagamento;
import com.javorders.pagamentoservice.infrastructure.controller.PagamentoController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PagamentoController.class)
class PagamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SolicitarPagamentoUsecase solicitarPagamentoUsecase;

    @MockBean
    private BuscarPagamentoPorUuidUsecase buscarPagamentoPorUuidUsecase;

    @MockBean
    private EstornarPagamentoUsecase estornarPagamentoUsecase;

    @Test
    void deveSolicitarPagamentoComSucesso() throws Exception {
        UUID uuid = UUID.randomUUID();
        Pagamento pagamento = new Pagamento(
                1L,
                123L,
                new BigDecimal("150.00"),
                uuid,
                "1234567890123456",
                StatusPagamento.APROVADO
        );

        Mockito.when(solicitarPagamentoUsecase.executar(Mockito.any(Pagamento.class)))
                .thenReturn(pagamento);

        var requestJson = """
                {
                    "clienteId": 123,
                    "valor": 150.00,
                    "numeroCartao": "1234567890123456"
                }
                """;

        mockMvc.perform(post("/pagamentos")
                                .contentType(APPLICATION_JSON)
                                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuidTransacao").value(uuid.toString()))
                .andExpect(jsonPath("$.status").value("APROVADO"));
    }

    @Test
    void deveBuscarPagamentoPorUuid() throws Exception {
        UUID uuid = UUID.randomUUID();
        Pagamento pagamento = new Pagamento(
                1L,
                123L,
                new BigDecimal("150.00"),
                uuid,
                "1234567890123456",
                StatusPagamento.APROVADO
        );

        Mockito.when(buscarPagamentoPorUuidUsecase.executar(uuid))
                .thenReturn(pagamento);

        mockMvc.perform(get("/pagamentos/{uuid}", uuid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuidTransacao").value(uuid.toString()))
                .andExpect(jsonPath("$.status").value("APROVADO"));
    }

    @Test
    void deveEstornarPagamento() throws Exception {
        UUID uuid = UUID.randomUUID();

        mockMvc.perform(patch("/pagamentos/{uuid}/estornar", uuid))
                .andExpect(status().isNoContent());

        Mockito.verify(estornarPagamentoUsecase)
                .estornar(uuid);
    }
}