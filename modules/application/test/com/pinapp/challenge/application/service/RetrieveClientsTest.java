package com.pinapp.challenge.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pinapp.challenge.domain.model.Client;
import com.pinapp.challenge.domain.port.ClientRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class RetrieveClientsTest {

  @Mock
  ClientRepository clientRepository;

  @InjectMocks
  RetrieveClients retrieveClients;

  @Test
  void whenFetchingClients_thenReturnPaginatedResult() {
    // Arrange
    Pageable pageable = PageRequest.of(0, 10);
    List<Client> clientList = List.of(new Client(), new Client());
    Page<Client> expectedPage = new PageImpl<>(clientList, pageable, clientList.size());

    when(clientRepository.findAll(pageable)).thenReturn(expectedPage);

    // Act
    Page<Client> result = retrieveClients.execute(pageable);

    // Assert
    assertEquals(2, result.getTotalElements());
    verify(clientRepository).findAll(pageable);
  }
}
