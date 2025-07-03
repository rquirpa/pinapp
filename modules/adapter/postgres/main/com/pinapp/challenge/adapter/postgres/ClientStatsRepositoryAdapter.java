package com.pinapp.challenge.adapter.postgres;

import com.pinapp.challenge.adapter.postgres.entity.ClientStatsEntity;
import com.pinapp.challenge.adapter.postgres.jpa.ClientStatsJpaRepository;
import com.pinapp.challenge.adapter.postgres.mapper.ClientStatsMapper;
import com.pinapp.challenge.domain.model.ClientStats;
import com.pinapp.challenge.domain.port.ClientStatsRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ClientStatsRepositoryAdapter implements ClientStatsRepository {

  private final ClientStatsJpaRepository jpaRepository;
  private final ClientStatsMapper mapper;

  @Override
  public ClientStats save(ClientStats clientStats) {
    ClientStatsEntity entity = mapper.map(clientStats);
    ClientStatsEntity savedEntity = jpaRepository.saveAndFlush(entity);

    return mapper.map(savedEntity);
  }

  @Override
  public ClientStats find() {
    Optional<ClientStatsEntity> entity = jpaRepository.findById(0);

    return entity
        .map(mapper::map)
        .orElse(new ClientStats(0.0, 0.0));
  }
}
