package com.pinapp.challenge.adapter.postgres;

import com.pinapp.challenge.adapter.postgres.jpa.LifeExpectancyJpaRepository;
import com.pinapp.challenge.adapter.postgres.mapper.LifeExpectancyMapper;
import com.pinapp.challenge.domain.model.LifeExpectancy;
import com.pinapp.challenge.domain.port.LifeExpectancyRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class LifeExpectancyRepositoryAdapter implements LifeExpectancyRepository {

  private final LifeExpectancyJpaRepository repository;
  private final LifeExpectancyMapper mapper;

  @Override
  public Optional<LifeExpectancy> findById(Integer year) {
    return repository.findById(year)
        .map(mapper::map);
  }
}
