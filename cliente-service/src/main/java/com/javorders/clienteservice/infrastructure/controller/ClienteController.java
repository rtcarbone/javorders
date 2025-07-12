package com.javorders.clienteservice.infrastructure.controller;

import com.javorders.clienteservice.application.usecases.AlterarClienteUsecase;
import com.javorders.clienteservice.application.usecases.CadastrarClienteUsecase;
import com.javorders.clienteservice.application.usecases.ConsultarClientesUsecase;
import com.javorders.clienteservice.application.usecases.DeletarClienteUsecase;
import com.javorders.clienteservice.domain.model.Cliente;
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
    private final ConsultarClientesUsecase consultarClientesUsecase;

    @PostMapping
    public ResponseEntity<Cliente> cadastrar(@RequestBody Cliente cliente) {
        var salvo = cadastrarClienteUsecase.executar(cliente);
        return ResponseEntity.ok(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> alterar(@PathVariable Long id, @RequestBody Cliente cliente) {
        var atualizado = alterarClienteUsecase.executar(id, cliente);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        deletarClienteUsecase.executar(id);
        return ResponseEntity.noContent()
                .build();
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodos() {
        var clientes = consultarClientesUsecase.executar();
        return ResponseEntity.ok(clientes);
    }

}