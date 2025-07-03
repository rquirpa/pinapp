package com.pinapp.challenge.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pinapp.challenge.application.exception.ApplicationException;
import com.pinapp.challenge.domain.model.Client;
import com.pinapp.challenge.domain.model.LifeExpectancy;
import com.pinapp.challenge.domain.model.Sex;
import com.pinapp.challenge.domain.port.ClientRepository;
import com.pinapp.challenge.domain.port.EventPublisher;
import com.pinapp.challenge.domain.port.LifeExpectancyRepository;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateClientTest {

  @Mock
  ClientRepository clientRepository;

  @Mock
  LifeExpectancyRepository lifeExpectancyRepository;

  @Mock
  EventPublisher eventPublisher;

  @InjectMocks
  CreateClient createClient;

  Client client;

  @BeforeEach
  void setUp() {
    client = new Client();
    client.setFirstName("Juan");
    client.setBirthDate(LocalDate.of(1990, 1, 1));
    client.setSex(Sex.MALE);
  }

  @Test
  void whenCreatingMaleClient_thenCalculateDeathDate_saveClient_andPublishEvent() {
    // Arrange
    LifeExpectancy expectancy = new LifeExpectancy(1990, 75.0, 82.0, 80.0);
    when(lifeExpectancyRepository.findById(1990)).thenReturn(Optional.of(expectancy));

    Client savedClient = new Client();
    when(clientRepository.save(any())).thenReturn(savedClient);

    // Act
    Client result = createClient.execute(client);

    // Assert
    LocalDate expectedDeathDate = client.getBirthDate().plusDays((long) (82.0 * 365.25));

    assertEquals(expectedDeathDate, client.getEstimatedDeathDate());
    verify(clientRepository).save(client);
    verify(eventPublisher).publishEvent("client:created", savedClient);
    assertEquals(savedClient, result);
  }

  @Test
  void whenCreatingFemaleClient_thenCalculateDeathDate_saveClient_andPublishEvent() {
    // Arrange
    client.setSex(Sex.FEMALE);

    LifeExpectancy expectancy = new LifeExpectancy(1990, 75.0, 82.0, 80.0);
    when(lifeExpectancyRepository.findById(1990)).thenReturn(Optional.of(expectancy));

    Client savedClient = new Client();
    when(clientRepository.save(any())).thenReturn(savedClient);

    // Act
    Client result = createClient.execute(client);

    // Assert
    LocalDate expectedDeathDate = client.getBirthDate().plusDays((long) (75.0 * 365.25));

    assertEquals(expectedDeathDate, client.getEstimatedDeathDate());
    verify(clientRepository).save(client);
    verify(eventPublisher).publishEvent("client:created", savedClient);
    assertEquals(savedClient, result);
  }

  @Test
  void whenLifeExpectancyNotFound_ThenThrowException() {
    // Arrange
    when(lifeExpectancyRepository.findById(1990)).thenReturn(Optional.empty());

    // Act & Assert
    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      createClient.execute(client);
    });

    assertEquals("No se encontró expectativa de vida", exception.getMessage());
    verify(clientRepository, never()).save(any());
    verify(eventPublisher, never()).publishEvent(any(), any());
  }
}
