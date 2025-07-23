package com.javorders.pedidoservice.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javorders.pedidoservice.PedidoServiceApplication;
import com.javorders.pedidoservice.infrastructure.dto.ItemPedidoDTO;
import com.javorders.pedidoservice.infrastructure.dto.PedidoRequestDTO;
import com.javorders.pedidoservice.infrastructure.persistence.entity.PedidoEntity;
import com.javorders.pedidoservice.infrastructure.persistence.repository.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        classes = PedidoServiceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PedidoComponentTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarEConsultarPedido() {
        // Preparar o DTO
        PedidoRequestDTO requestDTO = new PedidoRequestDTO(
                1L,
                "1234567890123456",
                List.of(new ItemPedidoDTO("SKU123", 2))
        );

        // PUT /pedidos/1
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PedidoRequestDTO> requestEntity = new HttpEntity<>(requestDTO, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/pedidos/1",
                HttpMethod.PUT,
                requestEntity,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verificar se o pedido foi persistido no banco
        PedidoEntity entity = repository.findById(1L)
                .orElse(null);
        assertThat(entity).isNotNull();
        assertThat(entity.getClienteId()).isEqualTo(1L);
        assertThat(entity.getItens()).hasSize(1);
        assertThat(entity.getItens()
                           .get(0)
                           .getSku()).isEqualTo("SKU123");
        assertThat(entity.getItens()
                           .get(0)
                           .getQuantidade()).isEqualTo(2);
    }
}