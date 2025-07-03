package com.pinapp.challenge.application.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.pinapp.challenge.domain.model.ClientStats;
import com.pinapp.challenge.domain.port.ClientRepository;
import com.pinapp.challenge.domain.port.ClientStatsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateClientStatsTest {

  @Mock
  ClientRepository clientRepository;

  @Mock
  ClientStatsRepository clientStatsRepository;

  @InjectMocks
  UpdateClientStats updateClientStats;

  @Test
  void whenUpdatingClientStats_thenStatsAreUpdated() {
    // Arrange
    ClientStats dummyStats = new ClientStats(100.0, 75.5);

    when(clientRepository.getClientAgeStats()).thenReturn(dummyStats);

    // Act
    updateClientStats.execute();

    // Assert
    verify(clientRepository).getClientAgeStats();
    verify(clientStatsRepository).save(dummyStats);
    verifyNoMoreInteractions(clientRepository, clientStatsRepository);
  }
}
