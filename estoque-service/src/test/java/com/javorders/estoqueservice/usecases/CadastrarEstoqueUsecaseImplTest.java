package com.javorders.estoqueservice.usecases;

import com.javorders.estoqueservice.application.usecases.impl.CadastrarEstoqueUsecaseImpl;
import com.javorders.estoqueservice.domain.gateways.EstoqueGateway;
import com.javorders.estoqueservice.domain.model.Estoque;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CadastrarEstoqueUsecaseImplTest {

    private EstoqueGateway estoqueGateway;
    private CadastrarEstoqueUsecaseImpl usecase;

    @BeforeEach
    void setup() {
        estoqueGateway = mock(EstoqueGateway.class);
        usecase = new CadastrarEstoqueUsecaseImpl(estoqueGateway);
    }

    @Test
    void deveCadastrarEstoqueComSucesso() {
        // Arrange
        Estoque estoque = Estoque.builder()
                .sku("PROD001")
                .quantidade(10)
                .build();

        when(estoqueGateway.buscarPorSku("PROD001")).thenReturn(Optional.empty());
        when(estoqueGateway.salvar(estoque)).thenReturn(estoque);

        // Act
        Estoque resultado = usecase.executar(estoque);

        // Assert
        assertNotNull(resultado);
        assertEquals("PROD001", resultado.getSku());
        verify(estoqueGateway).salvar(estoque);
    }

    @Test
    void deveLancarExcecaoQuandoSkuJaExiste() {
        // Arrange
        Estoque estoque = Estoque.builder()
                .sku("PROD001")
                .quantidade(10)
                .build();

        when(estoqueGateway.buscarPorSku("PROD001")).thenReturn(Optional.of(estoque));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> usecase.executar(estoque));
        assertEquals("Já existe um estoque cadastrado com o SKU informado.", exception.getMessage());
        verify(estoqueGateway, never()).salvar(any());
    }
}