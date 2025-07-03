package com.pinapp.challenge.adapter.postgres;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pinapp.challenge.adapter.postgres.entity.ClientEntity;
import com.pinapp.challenge.adapter.postgres.jpa.ClientJpaRepository;
import com.pinapp.challenge.adapter.postgres.mapper.ClientMapper;
import com.pinapp.challenge.adapter.postgres.projection.ClientAgeStatsProjection;
import com.pinapp.challenge.domain.model.Client;
import com.pinapp.challenge.domain.model.ClientStats;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class ClientRepositoryAdapterTest {

  @Mock
  private ClientJpaRepository jpaRepository;

  @Spy
  private ClientMapper mapper;

  @InjectMocks
  private ClientRepositoryAdapter adapter;

  @Test
  void whenSavingClient_thenReturnPersistedClient() {
    // Arrange
    Client client = new Client();
    ClientEntity entity = new ClientEntity();
    ClientEntity savedEntity = new ClientEntity();
    Client savedClient = new Client();

    when(mapper.map(client)).thenReturn(entity);
    when(jpaRepository.saveAndFlush(entity)).thenReturn(savedEntity);
    when(mapper.map(savedEntity)).thenReturn(savedClient);

    // Act
    Client result = adapter.save(client);

    // Assert
    assertNotNull(result);
    assertEquals(savedClient, result);

    verify(mapper).map(client);
    verify(jpaRepository).saveAndFlush(entity);
    verify(mapper).map(savedEntity);
  }

  @Test
  void whenFetchingClients_thenReturnPaginatedResult() {}
  void findAll_shouldReturnPageOfClientsMapped() {
    // Arrange
    Pageable pageable = Pageable.unpaged();
    ClientEntity entity1 = new ClientEntity();
    ClientEntity entity2 = new ClientEntity();

    Client client1 = new Client();
    Client client2 = new Client();

    Page<ClientEntity> entityPage = new PageImpl<>(List.of(entity1, entity2));

    when(jpaRepository.findAll(pageable)).thenReturn(entityPage);
    when(mapper.map(entity1)).thenReturn(client1);
    when(mapper.map(entity2)).thenReturn(client2);

    // Act
    Page<Client> result = adapter.findAll(pageable);

    // Assert
    assertNotNull(result);
    assertEquals(2, result.getContent().size());
    assertEquals(client1, result.getContent().get(0));
    assertEquals(client2, result.getContent().get(1));

    verify(jpaRepository).findAll(pageable);
    verify(mapper).map(entity1);
    verify(mapper).map(entity2);
  }

  @Test
  void whenGettingClientStats_thenReturnStats() {
    // Arrange
    ClientAgeStatsProjection projection = mock(ClientAgeStatsProjection.class);

    when(jpaRepository.getClientAgeStats()).thenReturn(projection);
    when(projection.getAverageAge()).thenReturn(30.5);
    when(projection.getStandardDeviationAge()).thenReturn(4.5);

    // Act
    ClientStats stats = adapter.getClientAgeStats();

    // Assert
    assertNotNull(stats);
    assertEquals(30.5, stats.averageAge());
    assertEquals(4.5, stats.standardDeviationAge());

    verify(jpaRepository).getClientAgeStats();
    verify(projection).getAverageAge();
    verify(projection).getStandardDeviationAge();
  }
}
