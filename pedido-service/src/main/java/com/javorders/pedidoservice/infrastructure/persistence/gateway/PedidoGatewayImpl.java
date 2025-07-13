package com.javorders.pedidoservice.infrastructure.persistence.gateway;

import com.javorders.pedidoservice.domain.gateways.PedidoGateway;
import com.javorders.pedidoservice.domain.model.Pedido;
import com.javorders.pedidoservice.infrastructure.persistence.mapper.PedidoMapper;
import com.javorders.pedidoservice.infrastructure.persistence.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PedidoGatewayImpl implements PedidoGateway {

    private final PedidoRepository repository;

    @Override
    public Pedido salvar(Pedido pedido) {
        var entity = PedidoMapper.toEntity(pedido);
        var saved = repository.save(entity);
        return PedidoMapper.toDomain(saved);
    }

    @Override
    public Optional<Pedido> buscarPorId(Long id) {
        return repository.findById(id)
                .map(PedidoMapper::toDomain);
    }

    @Override
    public Optional<Pedido> buscarPorUuidPagamento(String uuid) {
        // este método será implementado futuramente com integração ao pagamento-service
        return Optional.empty();
    }

}