package com.javorders.pedidoservice.infrastructure.controller;

import com.javorders.pedidoservice.application.usecases.RegistrarPedidoUsecase;
import com.javorders.pedidoservice.infrastructure.controller.dto.PedidoRequestDTO;
import com.javorders.pedidoservice.infrastructure.controller.dto.PedidoResponseDTO;
import com.javorders.pedidoservice.infrastructure.controller.mapper.PedidoDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final RegistrarPedidoUsecase registrarPedidoUsecase;

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> registrar(@RequestBody PedidoRequestDTO dto) {
        var pedido = PedidoDTOMapper.toDomain(dto);
        var salvo = registrarPedidoUsecase.executar(pedido);
        return ResponseEntity.ok(PedidoDTOMapper.toResponse(salvo));
    }

}