package com.pinapp.challenge.entrypoint.sns;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinapp.challenge.application.service.UpdateClientStats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ClientCreatedEventHandlerTest {

  @Mock
  private UpdateClientStats updateClientStats;

  @InjectMocks
  private ClientCreatedEventHandler handler;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void whenGetType_thenReturnClientCreatedType() {
    // Act & Assert
    assertEquals("client:created", handler.getType());
  }

  @Test
  void whenReceivingEvent_thenExecuteUpdateClientStats() throws Exception {
    // Arrange
    JsonNode dummyMessage = new ObjectMapper().readTree("{\"some\": \"data\"}");

    // Act
    handler.process(dummyMessage);

    // Assert
    verify(updateClientStats).execute();
    verifyNoMoreInteractions(updateClientStats);
  }
}
