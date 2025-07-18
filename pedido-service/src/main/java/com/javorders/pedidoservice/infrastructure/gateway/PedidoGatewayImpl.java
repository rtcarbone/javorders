package com.javorders.pedidoservice.infrastructure.gateway;

import com.javorders.pedidoservice.domain.gateways.PedidoGateway;
import com.javorders.pedidoservice.domain.model.Pedido;
import com.javorders.pedidoservice.infrastructure.persistence.mapper.PedidoMapper;
import com.javorders.pedidoservice.infrastructure.persistence.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PedidoGatewayImpl implements PedidoGateway {

    private final PedidoRepository repository;

    @Override
    public Pedido salvar(Pedido pedido) {
        var entity = PedidoMapper.toEntity(pedido);
        var salvo = repository.save(entity);
        return PedidoMapper.toDomain(salvo);
    }

    @Override
    public Optional<Pedido> buscarPorId(Long id) {
        return repository.findById(id)
                .map(PedidoMapper::toDomain);
    }

    @Override
    public Optional<Pedido> buscarPorUuidTransacao(UUID uuidTransacao) {
        return repository.findByUuidTransacao(uuidTransacao)
                .map(PedidoMapper::toDomain);
    }

    @Override
    public List<Pedido> buscarTodos() {
        return repository.findAll()
                .stream()
                .map(PedidoMapper::toDomain)
                .toList();
    }

    @Override
    public void deletarPorId(Long id) {
        repository.deleteById(id);
    }
}