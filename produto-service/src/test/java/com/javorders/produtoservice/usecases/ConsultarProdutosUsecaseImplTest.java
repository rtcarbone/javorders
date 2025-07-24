package com.javorders.produtoservice.usecases;

import com.javorders.produtoservice.application.usecases.impl.ConsultarProdutosUsecaseImpl;
import com.javorders.produtoservice.domain.gateways.ProdutoGateway;
import com.javorders.produtoservice.domain.model.Produto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ConsultarProdutosUsecaseImplTest {

    @Mock
    private ProdutoGateway produtoGateway;

    @InjectMocks
    private ConsultarProdutosUsecaseImpl usecase;

    @Test
    void deveRetornarTodosOsProdutos() {
        // Arrange
        List<Produto> produtos = List.of(
                new Produto(1L, "Produto 1", "sku-1", new BigDecimal("10.00")),
                new Produto(2L, "Produto 2", "sku-2", new BigDecimal("20.00"))
        );

        Mockito.when(produtoGateway.listarTodos())
                .thenReturn(produtos);

        // Act
        List<Produto> resultado = usecase.executar();

        // Assert
        assertEquals(2, resultado.size());
        assertEquals("sku-1", resultado.get(0)
                .getSku());
        assertEquals("sku-2", resultado.get(1)
                .getSku());
    }
}
