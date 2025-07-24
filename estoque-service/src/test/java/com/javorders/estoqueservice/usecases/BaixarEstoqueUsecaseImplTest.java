package com.javorders.estoqueservice.usecases;

import com.javorders.estoqueservice.application.usecases.impl.BaixarEstoqueUsecaseImpl;
import com.javorders.estoqueservice.domain.gateways.EstoqueGateway;
import com.javorders.estoqueservice.domain.model.Estoque;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BaixarEstoqueUsecaseImplTest {

    private EstoqueGateway estoqueGateway;
    private BaixarEstoqueUsecaseImpl usecase;

    @BeforeEach
    void setup() {
        estoqueGateway = mock(EstoqueGateway.class);
        usecase = new BaixarEstoqueUsecaseImpl(estoqueGateway);
    }

    @Test
    void deveBaixarEstoqueComSucesso() {
        // Arrange
        String sku = "ABC123";
        int quantidade = 3;
        Estoque estoque = Estoque.builder()
                .sku(sku)
                .quantidade(5)
                .build();

        when(estoqueGateway.buscarPorSku(sku)).thenReturn(Optional.of(estoque));

        // Act
        usecase.executar(sku, quantidade);

        // Assert
        verify(estoqueGateway).baixarEstoque(sku, quantidade);
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoEncontrado() {
        // Arrange
        String sku = "NAO_EXISTE";
        when(estoqueGateway.buscarPorSku(sku)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> usecase.executar(sku, 1));
        assertEquals("Produto não encontrado: NAO_EXISTE", exception.getMessage());
        verify(estoqueGateway, never()).baixarEstoque(anyString(), anyInt());
    }

    @Test
    void deveLancarExcecaoQuandoEstoqueInsuficiente() {
        // Arrange
        String sku = "SKU123";
        Estoque estoque = Estoque.builder()
                .sku(sku)
                .quantidade(2)
                .build();

        when(estoqueGateway.buscarPorSku(sku)).thenReturn(Optional.of(estoque));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> usecase.executar(sku, 5));
        assertEquals("Estoque insuficiente para o produto: SKU123", exception.getMessage());
        verify(estoqueGateway, never()).baixarEstoque(anyString(), anyInt());
    }
}