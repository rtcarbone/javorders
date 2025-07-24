package com.javorders.estoqueservice.usecases;

import com.javorders.estoqueservice.application.usecases.impl.RestaurarEstoqueUsecaseImpl;
import com.javorders.estoqueservice.domain.gateways.EstoqueGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class RestaurarEstoqueUsecaseImplTest {

    private EstoqueGateway estoqueGateway;
    private RestaurarEstoqueUsecaseImpl usecase;

    @BeforeEach
    void setup() {
        estoqueGateway = mock(EstoqueGateway.class);
        usecase = new RestaurarEstoqueUsecaseImpl(estoqueGateway);
    }

    @Test
    void deveRestaurarEstoqueComSucesso() {
        // Arrange
        String sku = "PROD123";
        Integer quantidade = 5;

        // Act
        usecase.executar(sku, quantidade);

        // Assert
        verify(estoqueGateway, times(1)).restaurarEstoque(sku, quantidade);
    }
}
