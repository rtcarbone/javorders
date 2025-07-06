package com.javorders.pedidoservice.infrastructure.persistence;

import com.javorders.pedidoservice.domain.gateways.PedidoGateway;
import com.javorders.pedidoservice.domain.model.Pedido;
import com.javorders.pedidoservice.infrastructure.persistence.mapper.PedidoMapper;
import com.javorders.pedidoservice.infrastructure.persistence.repository.PedidoRepository;
import org.springframework.stereotype.Component;

@Component
public class PedidoGatewayImpl implements PedidoGateway {

    private final PedidoRepository repository;

    public PedidoGatewayImpl(PedidoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Pedido salvar(Pedido pedido) {
        var entity = PedidoMapper.toEntity(pedido);
        return PedidoMapper.toDomain(repository.save(entity));
    }
}