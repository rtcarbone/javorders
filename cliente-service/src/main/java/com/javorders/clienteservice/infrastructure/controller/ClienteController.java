package com.javorders.clienteservice.infrastructure.controller;

import com.javorders.clienteservice.application.usecases.CadastrarClienteUsecase;
import com.javorders.clienteservice.domain.model.Cliente;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final CadastrarClienteUsecase cadastrarClienteUsecase;

    public ClienteController(CadastrarClienteUsecase cadastrarClienteUsecase) {
        this.cadastrarClienteUsecase = cadastrarClienteUsecase;
    }

    @PostMapping
    public ResponseEntity<Cliente> cadastrar(@RequestBody Cliente cliente) {
        return ResponseEntity.ok(cadastrarClienteUsecase.executar(cliente));
    }
}