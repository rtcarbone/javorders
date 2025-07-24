package com.javorders.pedidoreceiver.infrastructure.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javorders.pedidoreceiver.domain.gateways.PedidoPublisherGateway;
import com.javorders.pedidoreceiver.domain.model.Pedido;
import com.javorders.pedidoreceiver.infrastructure.dto.PedidoDTO;
import com.javorders.pedidoreceiver.infrastructure.mapper.PedidoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PedidoPublisher implements PedidoPublisherGateway {

    private final RabbitTemplate rabbitTemplate;
    private final PedidoMapper mapper;
    private final ObjectMapper objectMapper;

    @Override
    public void publicar(Pedido pedido) {
        try {
            PedidoDTO dto = mapper.toDTO(pedido);
            String mensagemJson = objectMapper.writeValueAsString(dto);

            rabbitTemplate.convertAndSend("", "novo-pedido-queue", mensagemJson);
            log.info("Pedido publicado com sucesso na fila: {}", mensagemJson);

        } catch (JsonProcessingException e) {
            log.error("Erro ao serializar PedidoDTO para JSON", e);
            throw new RuntimeException("Erro ao publicar mensagem no RabbitMQ", e);
        }
    }

}