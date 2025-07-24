package com.javorders.produtoservice.usecases;

import com.javorders.produtoservice.application.usecases.impl.DeletarProdutoUsecaseImpl;
import com.javorders.produtoservice.domain.gateways.ProdutoGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeletarProdutoUsecaseImplTest {

    @Mock
    private ProdutoGateway gateway;

    @InjectMocks
    private DeletarProdutoUsecaseImpl usecase;

    @Test
    void deveDeletarProdutoComIdCorreto() {
        // Arrange
        Long id = 123L;

        // Act
        usecase.executar(id);

        // Assert
        verify(gateway, times(1)).deletar(id);
    }
}
