package com.javorders.produtoservice.infrastructure.controller;

import com.javorders.produtoservice.application.usecases.*;
import com.javorders.produtoservice.infrastructure.dto.ProdutoRequestDTO;
import com.javorders.produtoservice.infrastructure.dto.ProdutoResponseDTO;
import com.javorders.produtoservice.infrastructure.mapper.ProdutoRequestMapper;
import com.javorders.produtoservice.infrastructure.mapper.ProdutoResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final CadastrarProdutoUsecase cadastrarProdutoUsecase;
    private final AlterarProdutoUsecase alterarProdutoUsecase;
    private final DeletarProdutoUsecase deletarProdutoUsecase;
    private final ConsultarProdutosPorSkuUsecase consultarProdutosPorSkuUsecase;
    private final ConsultarProdutosUsecase consultarProdutosUsecase;

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> cadastrar(@RequestBody ProdutoRequestDTO dto) {
        var produto = cadastrarProdutoUsecase.executar(ProdutoRequestMapper.toDomain(dto));
        return ResponseEntity.ok(ProdutoResponseMapper.toResponse(produto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> alterar(@PathVariable Long id, @RequestBody ProdutoRequestDTO dto) {
        var produto = alterarProdutoUsecase.executar(id, ProdutoRequestMapper.toDomain(dto));
        return ResponseEntity.ok(ProdutoResponseMapper.toResponse(produto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        deletarProdutoUsecase.executar(id);
        return ResponseEntity.noContent()
                .build();
    }

    @PostMapping("/por-skus")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarPorSkus(@RequestBody List<String> skus) {
        var produtos = consultarProdutosPorSkuUsecase.executar(skus)
                .stream()
                .map(ProdutoResponseMapper::toResponse)
                .toList();

        return ResponseEntity.ok(produtos);
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listar() {
        var produtos = consultarProdutosUsecase.executar()
                .stream()
                .map(ProdutoResponseMapper::toResponse)
                .toList();
        return ResponseEntity.ok(produtos);
    }

}