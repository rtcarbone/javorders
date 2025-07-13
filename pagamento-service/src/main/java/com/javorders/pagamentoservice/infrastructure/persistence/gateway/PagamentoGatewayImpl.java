package com.javorders.pagamentoservice.infrastructure.persistence.gateway;

import com.javorders.pagamentoservice.domain.gateways.PagamentoGateway;
import com.javorders.pagamentoservice.domain.model.Pagamento;
import com.javorders.pagamentoservice.domain.model.StatusPagamento;
import com.javorders.pagamentoservice.infrastructure.persistence.entity.PagamentoEntity;
import com.javorders.pagamentoservice.infrastructure.persistence.mapper.PagamentoMapper;
import com.javorders.pagamentoservice.infrastructure.persistence.repository.PagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

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

    @Override
    public void estornar(UUID uuidTransacao) {
        PagamentoEntity entity = repository.findByUuidTransacao(uuidTransacao)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));

        entity.setStatus(StatusPagamento.ESTORNADO);
        repository.save(entity);
    }

}