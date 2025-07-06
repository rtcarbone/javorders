package com.javorders.estoqueservice.infrastructure.controller;

import com.javorders.estoqueservice.application.usecases.CadastrarEstoqueUsecase;
import com.javorders.estoqueservice.domain.model.Estoque;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estoques")
public class EstoqueController {

    private final CadastrarEstoqueUsecase usecase;

    public EstoqueController(CadastrarEstoqueUsecase usecase) {
        this.usecase = usecase;
    }

    @PostMapping
    public ResponseEntity<Estoque> cadastrar(@RequestBody Estoque estoque) {
        return ResponseEntity.ok(usecase.executar(estoque));
    }
}