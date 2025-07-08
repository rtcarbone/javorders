package com.javorders.produtoservice.infrastructure.controller;

import com.javorders.produtoservice.application.usecases.CadastrarProdutoUsecase;
import com.javorders.produtoservice.domain.model.Produto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final CadastrarProdutoUsecase cadastrarProdutoUsecase;

    public ProdutoController(CadastrarProdutoUsecase cadastrarProdutoUsecase) {
        this.cadastrarProdutoUsecase = cadastrarProdutoUsecase;
    }

    @PostMapping
    public ResponseEntity<Produto> cadastrar(@RequestBody Produto produto) {
        return ResponseEntity.ok(cadastrarProdutoUsecase.executar(produto));
    }

}