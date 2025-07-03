package com.pinapp.challenge.adapter.postgres;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.pinapp.challenge.adapter.postgres.entity.LifeExpectancyEntity;
import com.pinapp.challenge.adapter.postgres.jpa.LifeExpectancyJpaRepository;
import com.pinapp.challenge.adapter.postgres.mapper.LifeExpectancyMapper;
import com.pinapp.challenge.domain.model.LifeExpectancy;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LifeExpectancyRepositoryAdapterTest {

  @Mock
  LifeExpectancyJpaRepository repository;

  @Spy
  LifeExpectancyMapper mapper;

  @InjectMocks
  LifeExpectancyRepositoryAdapter adapter;

  @Test
  void whenFetchingLifeExpectancy_thenReturnPersistedLifeExpectancy() {
    // Arrange
    Integer year = 1980;
    var entity = new LifeExpectancyEntity();
    var domain = new LifeExpectancy(year, 80.2, 80.2, 80.2);

    when(repository.findById(year)).thenReturn(Optional.of(entity));
    when(mapper.map(entity)).thenReturn(domain);

    // Act
    Optional<LifeExpectancy> result = adapter.findById(year);

    // Assert
    assertTrue(result.isPresent());
    assertEquals(domain, result.get());

    verify(repository).findById(year);
    verify(mapper).map(entity);
  }

  @Test
  void whenFetchingLifeExpectancy_thenReturnEmpty() {
    // Arrange
    Integer year = 2000;

    when(repository.findById(year)).thenReturn(Optional.empty());

    // Act
    Optional<LifeExpectancy> result = adapter.findById(year);

    // Assert
    assertFalse(result.isPresent());

    verify(repository).findById(year);
    verifyNoInteractions(mapper);
  }
}
