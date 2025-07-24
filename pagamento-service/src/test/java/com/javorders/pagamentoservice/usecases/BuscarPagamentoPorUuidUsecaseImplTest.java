package com.javorders.pagamentoservice.usecases;

import com.javorders.pagamentoservice.application.usecases.impl.BuscarPagamentoPorUuidUsecaseImpl;
import com.javorders.pagamentoservice.domain.gateways.PagamentoGateway;
import com.javorders.pagamentoservice.domain.model.Pagamento;
import com.javorders.pagamentoservice.domain.model.StatusPagamento;
import com.javorders.pagamentoservice.domain.ports.out.PagamentoExternoGateway;
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
class BuscarPagamentoPorUuidUsecaseImplTest {

    @Mock
    private PagamentoGateway pagamentoGateway;

    @Mock
    private PagamentoExternoGateway pagamentoExternoGateway;

    @InjectMocks
    private BuscarPagamentoPorUuidUsecaseImpl usecase;

    @Test
    void deveBuscarEPersistirPagamentoComStatusAtualizado() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        Pagamento pagamento = new Pagamento(
                1L,
                123L,
                new BigDecimal("150.00"),
                uuid,
                "1234567890123456",
                StatusPagamento.PENDENTE
        );

        Mockito.when(pagamentoGateway.findByUuidTransacao(uuid))
                .thenReturn(Optional.of(pagamento));
        Mockito.when(pagamentoExternoGateway.buscarPagamento(uuid))
                .thenReturn(StatusPagamento.APROVADO);

        Pagamento pagamentoAtualizado = pagamento.toBuilder()
                .status(StatusPagamento.APROVADO)
                .build();
        Mockito.when(pagamentoGateway.salvar(Mockito.any(Pagamento.class)))
                .thenReturn(pagamentoAtualizado);

        // Act
        Pagamento resultado = usecase.executar(uuid);

        // Assert
        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(StatusPagamento.APROVADO, resultado.getStatus());
        Assertions.assertEquals(uuid, resultado.getUuidTransacao());

        Mockito.verify(pagamentoGateway)
                .findByUuidTransacao(uuid);
        Mockito.verify(pagamentoExternoGateway)
                .buscarPagamento(uuid);
        Mockito.verify(pagamentoGateway)
                .salvar(Mockito.any(Pagamento.class));
    }

    @Test
    void deveLancarExcecaoQuandoPagamentoNaoEncontrado() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        Mockito.when(pagamentoGateway.findByUuidTransacao(uuid))
                .thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            usecase.executar(uuid);
        });

        Assertions.assertTrue(ex.getMessage()
                                      .contains("Pagamento não encontrado para UUID: " + uuid));
        Mockito.verify(pagamentoGateway)
                .findByUuidTransacao(uuid);
        Mockito.verifyNoMoreInteractions(pagamentoExternoGateway);
    }
}