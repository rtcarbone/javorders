package com.javorders.estoqueservice.infrastructure.controller;

import com.javorders.estoqueservice.application.usecases.BaixarEstoqueUsecase;
import com.javorders.estoqueservice.application.usecases.CadastrarEstoqueUsecase;
import com.javorders.estoqueservice.application.usecases.ConsultarEstoqueUsecase;
import com.javorders.estoqueservice.application.usecases.RestaurarEstoqueUsecase;
import com.javorders.estoqueservice.domain.model.Estoque;
import com.javorders.estoqueservice.infrastructure.dto.EstoqueRequestDTO;
import com.javorders.estoqueservice.infrastructure.dto.EstoqueResponseDTO;
import com.javorders.estoqueservice.infrastructure.mapper.EstoqueDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estoques")
@RequiredArgsConstructor
public class EstoqueController {

    private final CadastrarEstoqueUsecase cadastrarEstoqueUsecase;
    private final ConsultarEstoqueUsecase consultarEstoqueUsecase;
    private final BaixarEstoqueUsecase baixarEstoqueUsecase;
    private final RestaurarEstoqueUsecase restaurarEstoqueUsecase;

    @PostMapping
    public ResponseEntity<EstoqueResponseDTO> cadastrar(@RequestBody EstoqueRequestDTO dto) {
        Estoque estoque = cadastrarEstoqueUsecase.executar(EstoqueDTOMapper.toDomain(dto));
        return ResponseEntity.ok(EstoqueDTOMapper.toResponse(estoque));
    }

    @GetMapping("/{sku}")
    public ResponseEntity<EstoqueResponseDTO> consultar(@PathVariable String sku) {
        return consultarEstoqueUsecase.executar(sku)
                .map(EstoqueDTOMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound()
                                .build());
    }

    @PatchMapping("/baixar")
    public ResponseEntity<Void> baixar(@RequestBody EstoqueRequestDTO dto) {
        baixarEstoqueUsecase.executar(dto.sku(), dto.quantidade());
        return ResponseEntity.noContent()
                .build();
    }

    @PatchMapping("/restaurar")
    public ResponseEntity<Void> restaurar(@RequestBody EstoqueRequestDTO dto) {
        restaurarEstoqueUsecase.executar(dto.sku(), dto.quantidade());
        return ResponseEntity.noContent()
                .build();
    }

}