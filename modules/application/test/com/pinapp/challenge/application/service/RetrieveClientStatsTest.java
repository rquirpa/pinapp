package com.pinapp.challenge.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pinapp.challenge.domain.model.ClientStats;
import com.pinapp.challenge.domain.port.ClientStatsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RetrieveClientStatsTest {

  @Mock
  ClientStatsRepository clientStatsRepository;

  @InjectMocks
  RetrieveClientStats retrieveClientStats;

  @Test
  void whenFetchingClientStats_thenReturnPersistedClientStats() {
    // Arrange
    ClientStats expectedStats = new ClientStats(100.0, 50.0);
    when(clientStatsRepository.find()).thenReturn(expectedStats);

    // Act
    ClientStats result = retrieveClientStats.execute();

    // Assert
    assertEquals(expectedStats, result);
    verify(clientStatsRepository).find();
  }
}
