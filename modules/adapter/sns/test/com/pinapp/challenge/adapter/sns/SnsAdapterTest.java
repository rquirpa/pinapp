package com.pinapp.challenge.adapter.sns;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinapp.challenge.adapter.sns.config.SnsProperties;
import com.pinapp.challenge.domain.model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

class SnsAdapterTest {

  @Mock
  private SnsProperties snsProperties;

  @Mock
  private SnsClient snsClient;

  @Mock
  private ObjectMapper objectMapper;

  @InjectMocks
  private SnsAdapter snsAdapter;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void whenPublishingEvent_thenEventIsPublished() throws Exception {
    // Arrange
    String topicArn = "arn:aws:sns:us-east-1:123456789012:my-topic";
    TestPayload payload = new TestPayload("data123");
    Event<TestPayload> event = new Event<>("TEST_TYPE", payload);
    String json = "{\"type\":\"TEST_TYPE\",\"payload\":{\"data\":\"data123\"}}";

    when(snsProperties.getTopicArn()).thenReturn(topicArn);
    when(objectMapper.writeValueAsString(event)).thenReturn(json);

    // Act
    snsAdapter.publishEvent("TEST_TYPE", payload);

    // Assert
    verify(snsClient).publish(any(PublishRequest.class));
  }

  @Test
  void whenPublishingEventFails_thenThrowException() throws Exception {
    // Arrange
    TestPayload payload = new TestPayload("fail");
    when(objectMapper.writeValueAsString(any())).thenThrow(new JsonProcessingException("error") {});

    // Act & Assert
    assertThrows(RuntimeException.class, () -> snsAdapter.publishEvent("ERROR", payload));
  }

  static class TestPayload {
    private final String data;

    public TestPayload(String data) {
      this.data = data;
    }

    public String getData() {
      return data;
    }
  }
}
