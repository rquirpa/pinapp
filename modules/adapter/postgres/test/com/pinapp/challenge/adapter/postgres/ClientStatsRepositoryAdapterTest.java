package com.pinapp.challenge.adapter.postgres;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.pinapp.challenge.adapter.postgres.entity.ClientStatsEntity;
import com.pinapp.challenge.adapter.postgres.jpa.ClientStatsJpaRepository;
import com.pinapp.challenge.adapter.postgres.mapper.ClientStatsMapper;
import com.pinapp.challenge.domain.model.ClientStats;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClientStatsRepositoryAdapterTest {

  @Mock
  ClientStatsJpaRepository jpaRepository;

  @Spy
  ClientStatsMapper mapper;

  @InjectMocks
  ClientStatsRepositoryAdapter adapter;

  @Test
  void whenSavingStats_thenReturnPersistedStats() {
    // Arrange
    ClientStats domainStats = new ClientStats(30.0, 5.0);
    ClientStatsEntity entity = new ClientStatsEntity();
    entity.setAverageAge(30.0);
    entity.setStandardDeviationAge(5.0);

    ClientStatsEntity savedEntity = new ClientStatsEntity();
    savedEntity.setAverageAge(30.0);
    savedEntity.setStandardDeviationAge(5.0);

    when(mapper.map(domainStats)).thenReturn(entity);
    when(jpaRepository.saveAndFlush(entity)).thenReturn(savedEntity);
    when(mapper.map(savedEntity)).thenReturn(domainStats);

    // Act
    ClientStats result = adapter.save(domainStats);

    // Assert
    assertEquals(domainStats, result);
    verify(mapper).map(domainStats);
    verify(jpaRepository).saveAndFlush(entity);
    verify(mapper).map(savedEntity);
  }

  @Test
  void whenFetchingStats_thenReturnPersistedStats() {
    // Arrange
    ClientStatsEntity entity = new ClientStatsEntity();
    entity.setAverageAge(28.0);
    entity.setStandardDeviationAge(3.2);

    ClientStats expected = new ClientStats(28.0, 3.2);

    when(jpaRepository.findById(0)).thenReturn(Optional.of(entity));
    when(mapper.map(entity)).thenReturn(expected);

    // Act
    ClientStats result = adapter.find();

    // Assert
    assertEquals(expected, result);
    verify(jpaRepository).findById(0);
    verify(mapper).map(entity);
  }

  @Test
  void whenFetchingStats_thenReturnDefaultStats() {
    // Assert
    when(jpaRepository.findById(0)).thenReturn(Optional.empty());

    // Act
    ClientStats result = adapter.find();

    // Arrange
    assertEquals(0.0, result.averageAge());
    assertEquals(0.0, result.standardDeviationAge());
    verify(jpaRepository).findById(0);
    verifyNoInteractions(mapper);
  }
}
