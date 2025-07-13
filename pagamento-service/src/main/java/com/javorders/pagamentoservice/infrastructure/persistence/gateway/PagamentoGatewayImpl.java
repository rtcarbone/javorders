package com.javorders.pagamentoservice.infrastructure.persistence.gateway;

import com.javorders.pagamentoservice.domain.gateways.PagamentoGateway;
import com.javorders.pagamentoservice.domain.model.Pagamento;
import com.javorders.pagamentoservice.infrastructure.persistence.mapper.PagamentoMapper;
import com.javorders.pagamentoservice.infrastructure.persistence.repository.PagamentoRepository;
import org.springframework.stereotype.Component;

@Component
public class PagamentoGatewayImpl implements PagamentoGateway {

    private final PagamentoRepository repository;

    public PagamentoGatewayImpl(PagamentoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Pagamento salvar(Pagamento pagamento) {
        var entity = PagamentoMapper.toEntity(pagamento);
        return PagamentoMapper.toDomain(repository.save(entity));
    }
}