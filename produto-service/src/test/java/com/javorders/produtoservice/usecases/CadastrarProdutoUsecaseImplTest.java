package com.javorders.produtoservice.usecases;

import com.javorders.produtoservice.application.usecases.impl.CadastrarProdutoUsecaseImpl;
import com.javorders.produtoservice.domain.gateways.ProdutoGateway;
import com.javorders.produtoservice.domain.model.Produto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CadastrarProdutoUsecaseImplTest {

    @Mock
    private ProdutoGateway gateway;

    @InjectMocks
    private CadastrarProdutoUsecaseImpl usecase;

    @Test
    void deveCadastrarProdutoComSucesso() {
        // Arrange
        Produto novoProduto = new Produto(null, "Produto Teste", "sku-123", new BigDecimal("199.99"));

        Mockito.when(gateway.buscarPorSku("sku-123"))
                .thenReturn(Optional.empty());

        Produto produtoSalvo = Produto.builder()
                .id(1L)
                .nome("Produto Teste")
                .sku("sku-123")
                .preco(new BigDecimal("199.99"))
                .build();

        Mockito.when(gateway.salvar(novoProduto))
                .thenReturn(produtoSalvo);

        // Act
        Produto resultado = usecase.executar(novoProduto);

        // Assert
        assertEquals(1L, resultado.getId());
        assertEquals("Produto Teste", resultado.getNome());
        assertEquals("sku-123", resultado.getSku());
        assertEquals(new BigDecimal("199.99"), resultado.getPreco());
    }

    @Test
    void deveLancarExcecaoQuandoSkuJaExiste() {
        // Arrange
        Produto novoProduto = new Produto(null, "Produto Teste", "sku-duplicado", new BigDecimal("100.00"));
        Produto existente = new Produto(99L, "Produto Existente", "sku-duplicado", new BigDecimal("150.00"));

        Mockito.when(gateway.buscarPorSku("sku-duplicado"))
                .thenReturn(Optional.of(existente));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usecase.executar(novoProduto);
        });

        assertEquals("Produto com SKU já existente.", exception.getMessage());
        Mockito.verify(gateway, Mockito.never())
                .salvar(Mockito.any());
    }
}