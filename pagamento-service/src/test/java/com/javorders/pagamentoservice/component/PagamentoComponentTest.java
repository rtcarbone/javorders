package com.javorders.pagamentoservice.component;

import com.javorders.pagamentoservice.infrastructure.persistence.repository.PagamentoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class PagamentoComponentTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("pagamentos")
            .withUsername("user")
            .withPassword("password");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void deveSolicitarPagamentoComSucesso() throws Exception {
        var requestJson = """
                {
                    "clienteId": 1,
                    "valor": 99.90,
                    "numeroCartao": "1234123412341234"
                }
                """;

        mockMvc.perform(post("/pagamentos")
                                .contentType(APPLICATION_JSON)
                                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuidTransacao").isNotEmpty())
                .andExpect(jsonPath("$.status").value("PENDENTE"));

        var pagamentos = pagamentoRepository.findAll();
        assertThat(pagamentos).hasSize(1);
        assertThat(pagamentos.get(0)
                           .getValor()).isEqualByComparingTo("99.90");
        assertThat(pagamentos.get(0)
                           .getStatus()
                           .name()).isEqualTo("PENDENTE");
    }
}
