package com.pinapp.challenge.entrypoint.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.pinapp.challenge.application.service.CreateClient;
import com.pinapp.challenge.application.service.RetrieveClientStats;
import com.pinapp.challenge.application.service.RetrieveClients;
import com.pinapp.challenge.domain.model.Client;
import com.pinapp.challenge.domain.model.ClientStats;
import com.pinapp.challenge.domain.model.Sex;
import com.pinapp.challenge.entrypoint.web.mapper.ClientRestMapper;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

class ClientControllerTest {

  @Mock
  private CreateClient createClient;

  @Mock
  private RetrieveClients retrieveClients;

  @Mock
  private RetrieveClientStats retrieveClientStats;

  @Mock
  private ClientRestMapper mapper;

  @InjectMocks
  private ClientController controller;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void whenClientIsValid_thenClientIsCreated() {
    // Arrange
    ClientRequest request = new ClientRequest(
        "Juan",
        "Pérez",
        Sex.MALE,
        LocalDate.of(2000, 1, 1)
    );

    Client mappedClient = new Client();
    Client createdClient = new Client();

    when(mapper.map(request)).thenReturn(mappedClient);
    when(createClient.execute(mappedClient)).thenReturn(createdClient);

    // Act
    var response = controller.create(request);

    // Assert
    assertEquals(201, response.getStatusCodeValue());
    assertEquals(createdClient, response.getBody());
  }

  @Test
  void whenClientsExist_thenReturnPageOfClients() {
    // Arrange
    Pageable pageable = PageRequest.of(0, 2);
    List<Client> clientList = List.of(new Client(), new Client());
    Page<Client> page = new PageImpl<>(clientList);

    when(retrieveClients.execute(pageable)).thenReturn(page);

    // Act
    var response = controller.retrieve(pageable);

    // Assert
    assertEquals(200, response.getStatusCodeValue());
    assertEquals(page, response.getBody());
  }

  @Test
  void whenStatsExist_thenReturnStats() {
    // Arrange
    ClientStats stats = new ClientStats(30.0, 5.0);
    when(retrieveClientStats.execute()).thenReturn(stats);

    // Act
    var response = controller.retrieveStats();

    // Assert
    assertEquals(200, response.getStatusCodeValue());
    assertEquals(stats, response.getBody());
  }
}
