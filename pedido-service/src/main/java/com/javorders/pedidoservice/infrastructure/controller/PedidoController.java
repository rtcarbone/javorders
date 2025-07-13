package com.javorders.pedidoservice.infrastructure.controller;

import com.javorders.pedidoservice.application.usecases.*;
import com.javorders.pedidoservice.domain.model.Pedido;
import com.javorders.pedidoservice.infrastructure.controller.dto.PedidoRequestDTO;
import com.javorders.pedidoservice.infrastructure.controller.dto.PedidoResponseDTO;
import com.javorders.pedidoservice.infrastructure.controller.mapper.PedidoDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final RabbitTemplate rabbitTemplate;
    private final RegistrarPedidoUsecase registrarPedidoUsecase;
    private final AlterarPedidoUsecase alterarPedidoUsecase;
    private final ConsultarPedidoUsecase consultarPedidoUsecase;
    private final ListarPedidosUsecase listarPedidosUsecase;
    private final DeletarPedidoUsecase deletarPedidoUsecase;

    @PostMapping
    public ResponseEntity<Void> publicarPedido(@RequestBody PedidoRequestDTO dto) {
        Pedido pedido = PedidoDTOMapper.toDomain(dto);
        rabbitTemplate.convertAndSend("novo-pedido-queue", pedido);
        return ResponseEntity.accepted()
                .build();
    }

    @PostMapping("/registrar")
    public ResponseEntity<PedidoResponseDTO> registrar(@RequestBody PedidoRequestDTO dto) {
        Pedido pedido = PedidoDTOMapper.toDomain(dto);
        Pedido salvo = registrarPedidoUsecase.executar(pedido);
        return ResponseEntity.ok(PedidoDTOMapper.toResponse(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> alterar(@PathVariable Long id, @RequestBody PedidoRequestDTO dto) {
        Pedido atualizado = alterarPedidoUsecase.executar(id, PedidoDTOMapper.toDomain(dto));
        return ResponseEntity.ok(PedidoDTOMapper.toResponse(atualizado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> consultar(@PathVariable Long id) {
        return consultarPedidoUsecase.executar(id)
                .map(PedidoDTOMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound()
                                .build());
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listar() {
        var pedidos = listarPedidosUsecase.executar()
                .stream()
                .map(PedidoDTOMapper::toResponse)
                .toList();
        return ResponseEntity.ok(pedidos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        deletarPedidoUsecase.executar(id);
        return ResponseEntity.noContent()
                .build();
    }

}