package com.javorders.clienteservice.infrastructure.controller;

import com.javorders.clienteservice.application.usecases.*;
import com.javorders.clienteservice.infrastructure.dto.ClienteRequestDTO;
import com.javorders.clienteservice.infrastructure.dto.ClienteResponseDTO;
import com.javorders.clienteservice.infrastructure.mapper.ClienteRequestMapper;
import com.javorders.clienteservice.infrastructure.mapper.ClienteResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final CadastrarClienteUsecase cadastrarClienteUsecase;
    private final AlterarClienteUsecase alterarClienteUsecase;
    private final DeletarClienteUsecase deletarClienteUsecase;
    private final BuscarClientePorIdUsecase buscarClientePorIdUsecase;
    private final ConsultarClientesUsecase consultarClientesUsecase;

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> cadastrar(@RequestBody ClienteRequestDTO dto) {
        var cliente = ClienteRequestMapper.toDomain(dto);
        var salvo = cadastrarClienteUsecase.executar(cliente);
        return ResponseEntity.ok(ClienteResponseMapper.toResponse(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> alterar(@PathVariable Long id, @RequestBody ClienteRequestDTO dto) {
        var cliente = ClienteRequestMapper.toDomain(dto);
        var atualizado = alterarClienteUsecase.executar(id, cliente);
        return ResponseEntity.ok(ClienteResponseMapper.toResponse(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        deletarClienteUsecase.executar(id);
        return ResponseEntity.noContent()
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Long id) {
        return buscarClientePorIdUsecase.executar(id)
                .map(ClienteResponseMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound()
                                .build());
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarTodos() {
        var clientes = consultarClientesUsecase.executar()
                .stream()
                .map(ClienteResponseMapper::toResponse)
                .toList();
        return ResponseEntity.ok(clientes);
    }

}