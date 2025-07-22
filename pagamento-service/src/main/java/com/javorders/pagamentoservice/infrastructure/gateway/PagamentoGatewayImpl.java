package com.javorders.pagamentoservice.infrastructure.gateway;

import com.javorders.pagamentoservice.domain.gateways.PagamentoGateway;
import com.javorders.pagamentoservice.domain.model.Pagamento;
import com.javorders.pagamentoservice.infrastructure.persistence.mapper.PagamentoMapper;
import com.javorders.pagamentoservice.infrastructure.persistence.repository.PagamentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class PagamentoGatewayImpl implements PagamentoGateway {

    private final PagamentoRepository repository;

    @Override
    public Pagamento salvar(Pagamento pagamento) {
        var entity = PagamentoMapper.toEntity(pagamento);
        log.info("Gravando no banco: uuid={}, status={}", entity.getUuidTransacao(), entity.getStatus());
        var saved = repository.save(entity);
        return PagamentoMapper.toDomain(saved);
    }

    @Override
    public Optional<Pagamento> findByUuidTransacao(UUID uuidTransacao) {
        return repository.findByUuidTransacao(uuidTransacao)
                .map(PagamentoMapper::toDomain);
    }

}