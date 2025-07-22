package com.javorders.pedidoservice.infrastructure.gateway;

import com.javorders.pedidoservice.domain.gateways.PagamentoGateway;
import com.javorders.pedidoservice.domain.model.Pagamento;
import com.javorders.pedidoservice.domain.model.Pedido;
import com.javorders.pedidoservice.domain.model.StatusPagamento;
import com.javorders.pedidoservice.infrastructure.dto.PagamentoRequestDTO;
import com.javorders.pedidoservice.infrastructure.dto.PagamentoResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class PagamentoGatewayImpl implements PagamentoGateway {

    private final WebClient webClient;

    public PagamentoGatewayImpl(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://pagamento-service:8080")
                .build();
    }

    @Override
    public Pagamento solicitarPagamento(Pedido pedido) {
        var pagamentoRequest = new PagamentoRequestDTO(
                pedido.getClienteId(),
                pedido.getValorTotal(),
                pedido.getNumeroCartao()
        );

        log.info(pagamentoRequest.toString());

        return webClient.post()
                .uri("/pagamentos")
                .bodyValue(pagamentoRequest)
                .retrieve()
                .bodyToMono(PagamentoResponseDTO.class)
                .map(dto -> new Pagamento(dto.uuidTransacao(), StatusPagamento.valueOf(dto.status())))
                .block();
    }

    @Override
    public Optional<Pagamento> buscarPorUuid(UUID uuid) {
        Pagamento pagamento = webClient.get()
                .uri("/pagamentos/{uuid}", uuid)
                .retrieve()
                .bodyToMono(Pagamento.class)
                .block();

        return Optional.ofNullable(pagamento);
    }

    @Override
    public void estornar(Pedido pedido) {
        webClient.post()
                .uri("/pagamentos/estornar")
                .bodyValue(pedido)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

}
