package com.pinapp.challenge;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.amazonaws.services.lambda.runtime.events.SNSEvent.SNS;
import com.amazonaws.services.lambda.runtime.events.SNSEvent.SNSRecord;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinapp.challenge.entrypoint.sns.EventHandler;
import java.util.List;
import java.util.function.Consumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FunctionConfigurationTest {

  private ObjectMapper mapper;
  private EventHandler eventHandler1;
  private EventHandler eventHandler2;
  private Consumer<SNSEvent> consumer;

  @BeforeEach
  void setup() {
    mapper = mock(ObjectMapper.class);
    eventHandler1 = mock(EventHandler.class);
    eventHandler2 = mock(EventHandler.class);

    List<EventHandler> handlers = List.of(eventHandler1, eventHandler2);

    FunctionConfiguration config = new FunctionConfiguration();
    consumer = config.eventListener(mapper, handlers);
  }

  @Test
  void whenTypeMatches_thenEventHandlerProcessCalled() throws Exception {
    // Arrange
    String messageJson = "{\"type\":\"TYPE1\",\"payload\":{\"key\":\"value\"}}";

    SNSEvent event = new SNSEvent();
    SNSRecord record = new SNSRecord();
    SNS sns = new SNS();
    sns.setMessage(messageJson);
    record.setSns(sns);
    event.setRecords(List.of(record));

    JsonNode rootNode = mock(JsonNode.class);
    JsonNode payloadNode = mock(JsonNode.class);

    when(mapper.readTree(messageJson)).thenReturn(rootNode);
    when(rootNode.get("type")).thenReturn(rootNode);
    when(rootNode.asText()).thenReturn("TYPE1");
    when(rootNode.get("payload")).thenReturn(payloadNode);

    when(eventHandler1.getType()).thenReturn("TYPE1");
    when(eventHandler2.getType()).thenReturn("TYPE2");

    // Act
    consumer.accept(event);

    // Assert
    verify(eventHandler1, times(1)).process(payloadNode);
    verify(eventHandler2, never()).process(any());
  }

  @Test
  void whenExceptionOccurs_thenExceptionIsThrown() throws Exception {
    // Arrange
    String messageJson = "{\"type\":\"TYPE1\",\"payload\":{}}";

    SNSEvent event = new SNSEvent();
    SNSRecord record = new SNSRecord();
    SNS sns = new SNS();
    sns.setMessage(messageJson);
    record.setSns(sns);
    event.setRecords(List.of(record));

    when(mapper.readTree(messageJson)).thenThrow(new JsonParseException(null, "error"));

    // Act & Assert
    assertThrows(RuntimeException.class, () -> consumer.accept(event));
  }
}
