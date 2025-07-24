package com.javorders.pedidoservice.component;

import com.javorders.pedidoservice.domain.model.ItemPedido;
import com.javorders.pedidoservice.domain.model.Pedido;
import com.javorders.pedidoservice.domain.model.StatusPedido;
import com.javorders.pedidoservice.infrastructure.persistence.entity.PedidoEntity;
import com.javorders.pedidoservice.infrastructure.persistence.mapper.PedidoMapper;
import com.javorders.pedidoservice.infrastructure.persistence.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
class PedidoComponentTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("pedidos")
            .withUsername("test")
            .withPassword("test");
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoMapper pedidoMapper;

    @DynamicPropertySource
    static void config(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    @Transactional
    void deveSalvarEPersistirPedido() {
        // Arrange
        Pedido pedido = Pedido.builder()
                .clienteId(1L)
                .numeroCartao("1234567890123456")
                .valorTotal(BigDecimal.valueOf(150.00))
                .status(StatusPedido.ABERTO)
                .itens(List.of(new ItemPedido("SKU-123", 2)))
                .build();

        // Act
        PedidoEntity entitySalvo = pedidoRepository.save(pedidoMapper.toEntity(pedido));
        Optional<PedidoEntity> encontrado = pedidoRepository.findById(entitySalvo.getId());

        // Assert
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get()
                           .getClienteId()).isEqualTo(1L);
        assertThat(encontrado.get()
                           .getItens()).hasSize(1);
    }
}