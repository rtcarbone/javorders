package com.javorders.pagamentoservice.usecases;

import com.javorders.pagamentoservice.application.usecases.impl.EstornarPagamentoUsecaseImpl;
import com.javorders.pagamentoservice.domain.gateways.PagamentoGateway;
import com.javorders.pagamentoservice.domain.model.Pagamento;
import com.javorders.pagamentoservice.domain.model.StatusPagamento;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class EstornarPagamentoUsecaseImplTest {

    @Mock
    private PagamentoGateway pagamentoGateway;

    @InjectMocks
    private EstornarPagamentoUsecaseImpl usecase;

    @Test
    void deveEstornarPagamentoComSucesso() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        Pagamento pagamento = new Pagamento(
                1L,
                100L,
                new BigDecimal("99.90"),
                uuid,
                "4111111111111111",
                StatusPagamento.APROVADO
        );

        Mockito.when(pagamentoGateway.findByUuidTransacao(uuid))
                .thenReturn(Optional.of(pagamento));

        Pagamento pagamentoEstornado = pagamento.toBuilder()
                .status(StatusPagamento.ESTORNADO)
                .build();

        Mockito.when(pagamentoGateway.salvar(Mockito.any(Pagamento.class)))
                .thenReturn(pagamentoEstornado);

        // Act
        usecase.estornar(uuid);

        // Assert
        Mockito.verify(pagamentoGateway)
                .findByUuidTransacao(uuid);
        Mockito.verify(pagamentoGateway)
                .salvar(Mockito.argThat(p ->
                                                p.getStatus() == StatusPagamento.ESTORNADO &&
                                                p.getUuidTransacao()
                                                        .equals(uuid)
                ));
    }

    @Test
    void deveLancarExcecaoQuandoPagamentoNaoEncontrado() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        Mockito.when(pagamentoGateway.findByUuidTransacao(uuid))
                .thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException ex = Assertions.assertThrows(RuntimeException.class, () -> {
            usecase.estornar(uuid);
        });

        Assertions.assertEquals("Pagamento não encontrado", ex.getMessage());
        Mockito.verify(pagamentoGateway)
                .findByUuidTransacao(uuid);
        Mockito.verifyNoMoreInteractions(pagamentoGateway);
    }
}