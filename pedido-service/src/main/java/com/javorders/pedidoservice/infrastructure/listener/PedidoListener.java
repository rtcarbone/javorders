package com.javorders.pedidoservice.infrastructure.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javorders.pedidoservice.application.usecases.ProcessarPedidoUsecase;
import com.javorders.pedidoservice.infrastructure.dto.PedidoRequestDTO;
import com.javorders.pedidoservice.infrastructure.mapper.PedidoDTOMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PedidoListener {

    private final ObjectMapper objectMapper;
    private final ProcessarPedidoUsecase processarPedidoUsecase;

    @RabbitListener(queues = "novo-pedido-queue")
    public void ouvirPedido(String mensagemJson) {
        try {
            PedidoRequestDTO dto = objectMapper.readValue(mensagemJson, PedidoRequestDTO.class);
            log.info("Pedido recebido da fila: {}", dto);

            var pedido = PedidoDTOMapper.toDomain(dto);
            processarPedidoUsecase.executar(pedido);

        } catch (Exception e) {
            log.error("Erro ao processar mensagem da fila: {}", mensagemJson, e);
        }
    }
}
