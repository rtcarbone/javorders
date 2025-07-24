package com.javorders.produtoservice.usecases;

import com.javorders.produtoservice.application.usecases.impl.AlterarProdutoUsecaseImpl;
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
class AlterarProdutoUsecaseImplTest {

    @Mock
    private ProdutoGateway gateway;

    @InjectMocks
    private AlterarProdutoUsecaseImpl usecase;

    @Test
    void deveAlterarProdutoComSucesso() {
        // Arrange
        Long id = 1L;
        Produto produtoOriginal = new Produto(null, "Produto Teste", "sku-123", new BigDecimal("100.00"));

        Produto produtoExistente = new Produto(id, "Produto Antigo", "sku-123", new BigDecimal("50.00"));

        Mockito.when(gateway.buscarPorSku("sku-123"))
                .thenReturn(Optional.of(produtoExistente));

        Produto produtoAtualizadoEsperado = Produto.builder()
                .id(id)
                .nome("Produto Teste")
                .sku("sku-123")
                .preco(new BigDecimal("100.00"))
                .build();

        Mockito.when(gateway.salvar(Mockito.any()))
                .thenReturn(produtoAtualizadoEsperado);

        // Act
        Produto resultado = usecase.executar(id, produtoOriginal);

        // Assert
        assertEquals(id, resultado.getId());
        assertEquals("Produto Teste", resultado.getNome());
        assertEquals("sku-123", resultado.getSku());
        assertEquals(new BigDecimal("100.00"), resultado.getPreco());
    }

    @Test
    void deveLancarExcecaoQuandoSkuPertenceAOutroProduto() {
        // Arrange
        Long idAtual = 1L;
        Long idOutroProduto = 2L;
        Produto produtoComMesmoSku = new Produto(idOutroProduto, "Outro Produto", "sku-repetido", new BigDecimal("99.99"));

        Produto novoProduto = new Produto(null, "Novo Produto", "sku-repetido", new BigDecimal("199.99"));

        Mockito.when(gateway.buscarPorSku("sku-repetido"))
                .thenReturn(Optional.of(produtoComMesmoSku));

        // Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            usecase.executar(idAtual, novoProduto);
        });

        assertEquals("SKU já utilizado por outro produto.", ex.getMessage());
        Mockito.verify(gateway, Mockito.never())
                .salvar(Mockito.any());
    }

    @Test
    void deveAlterarQuandoSkuNaoExiste() {
        // Arrange
        Long id = 1L;
        Produto produto = new Produto(null, "Produto X", "novo-sku", new BigDecimal("10.00"));

        Mockito.when(gateway.buscarPorSku("novo-sku"))
                .thenReturn(Optional.empty());

        Produto esperado = Produto.builder()
                .id(id)
                .nome("Produto X")
                .sku("novo-sku")
                .preco(new BigDecimal("10.00"))
                .build();

        Mockito.when(gateway.salvar(Mockito.any()))
                .thenReturn(esperado);

        // Act
        Produto resultado = usecase.executar(id, produto);

        // Assert
        assertEquals("novo-sku", resultado.getSku());
        assertEquals("Produto X", resultado.getNome());
        assertEquals(new BigDecimal("10.00"), resultado.getPreco());
    }
}
