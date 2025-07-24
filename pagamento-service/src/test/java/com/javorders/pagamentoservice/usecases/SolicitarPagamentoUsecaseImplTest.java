package com.javorders.pagamentoservice.usecases;

import com.javorders.pagamentoservice.application.usecases.impl.SolicitarPagamentoUsecaseImpl;
import com.javorders.pagamentoservice.domain.gateways.PagamentoGateway;
import com.javorders.pagamentoservice.domain.model.Pagamento;
import com.javorders.pagamentoservice.domain.model.StatusPagamento;
import com.javorders.pagamentoservice.domain.ports.out.PagamentoExternoGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class SolicitarPagamentoUsecaseImplTest {

    @Mock
    private PagamentoExternoGateway externoGateway;

    @Mock
    private PagamentoGateway pagamentoGateway;

    private SolicitarPagamentoUsecaseImpl usecase;

    @BeforeEach
    void setUp() {
        usecase = new SolicitarPagamentoUsecaseImpl(externoGateway, pagamentoGateway);
    }

    @Test
    void deveSolicitarPagamentoComSucesso() {
        // Arrange
        Pagamento pagamento = new Pagamento(
                null,
                123L,
                new BigDecimal("199.99"),
                null,
                "4111111111111111",
                null
        );

        UUID uuidGerado = UUID.randomUUID();

        Pagamento pagamentoComUuid = pagamento.toBuilder()
                .uuidTransacao(uuidGerado)
                .status(StatusPagamento.PENDENTE)
                .build();

        Mockito.when(externoGateway.solicitarPagamento(pagamento))
                .thenReturn(uuidGerado);
        Mockito.when(pagamentoGateway.salvar(Mockito.any(Pagamento.class)))
                .thenReturn(pagamentoComUuid);

        // Act
        Pagamento resultado = usecase.executar(pagamento);

        // Assert
        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(uuidGerado, resultado.getUuidTransacao());
        Assertions.assertEquals(StatusPagamento.PENDENTE, resultado.getStatus());

        Mockito.verify(externoGateway)
                .solicitarPagamento(pagamento);
        Mockito.verify(pagamentoGateway)
                .salvar(Mockito.argThat(p ->
                                                p.getUuidTransacao()
                                                        .equals(uuidGerado) &&
                                                p.getStatus() == StatusPagamento.PENDENTE
                ));
    }
}
