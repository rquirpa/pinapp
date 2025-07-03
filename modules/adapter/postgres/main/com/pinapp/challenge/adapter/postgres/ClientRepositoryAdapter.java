package com.pinapp.challenge.adapter.postgres;

import com.pinapp.challenge.adapter.postgres.entity.ClientEntity;
import com.pinapp.challenge.adapter.postgres.jpa.ClientJpaRepository;
import com.pinapp.challenge.adapter.postgres.mapper.ClientMapper;
import com.pinapp.challenge.adapter.postgres.projection.ClientAgeStatsProjection;
import com.pinapp.challenge.domain.model.Client;
import com.pinapp.challenge.domain.model.ClientStats;
import com.pinapp.challenge.domain.port.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ClientRepositoryAdapter implements ClientRepository {

  private final ClientJpaRepository jpaRepository;
  private final ClientMapper mapper;

  @Override
  public Client save(Client client) {
    ClientEntity entity = mapper.map(client);
    ClientEntity savedEntity = jpaRepository.saveAndFlush(entity);

    return mapper.map(savedEntity);
  }

  @Override
  public Page<Client> findAll(Pageable pageable) {
    return jpaRepository.findAll(pageable)
        .map(mapper::map);
  }

  @Override
  public ClientStats getClientAgeStats() {
    ClientAgeStatsProjection projection = jpaRepository.getClientAgeStats();

    return new ClientStats(projection.getAverageAge(), projection.getStandardDeviationAge());
  }

}
