package com.javorders.pagamentoservice.infrastructure.persistence.gateway;

import com.javorders.pagamentoservice.domain.gateways.PagamentoGateway;
import com.javorders.pagamentoservice.domain.model.Pagamento;
import com.javorders.pagamentoservice.infrastructure.persistence.mapper.PagamentoMapper;
import com.javorders.pagamentoservice.infrastructure.persistence.repository.PagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PagamentoGatewayImpl implements PagamentoGateway {

    private final PagamentoRepository repository;

    @Override
    public Pagamento salvar(Pagamento pagamento) {
        var entity = PagamentoMapper.toEntity(pagamento);
        var saved = repository.save(entity);
        return PagamentoMapper.toDomain(saved);
    }

}