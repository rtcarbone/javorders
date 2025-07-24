package com.javorders.estoqueservice.usecases;

import com.javorders.estoqueservice.application.usecases.impl.ConsultarEstoqueUsecaseImpl;
import com.javorders.estoqueservice.domain.gateways.EstoqueGateway;
import com.javorders.estoqueservice.domain.model.Estoque;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ConsultarEstoqueUsecaseImplTest {

    private EstoqueGateway estoqueGateway;
    private ConsultarEstoqueUsecaseImpl usecase;

    @BeforeEach
    void setup() {
        estoqueGateway = mock(EstoqueGateway.class);
        usecase = new ConsultarEstoqueUsecaseImpl(estoqueGateway);
    }

    @Test
    void deveRetornarEstoqueQuandoSkuExiste() {
        // Arrange
        String sku = "PROD001";
        Estoque estoque = Estoque.builder()
                .sku(sku)
                .quantidade(50)
                .build();

        when(estoqueGateway.buscarPorSku(sku)).thenReturn(Optional.of(estoque));

        // Act
        Optional<Estoque> resultado = usecase.executar(sku);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(sku, resultado.get()
                .getSku());
        verify(estoqueGateway).buscarPorSku(sku);
    }

    @Test
    void deveRetornarVazioQuandoSkuNaoExiste() {
        // Arrange
        String sku = "INEXISTENTE";
        when(estoqueGateway.buscarPorSku(sku)).thenReturn(Optional.empty());

        // Act
        Optional<Estoque> resultado = usecase.executar(sku);

        // Assert
        assertTrue(resultado.isEmpty());
        verify(estoqueGateway).buscarPorSku(sku);
    }
}