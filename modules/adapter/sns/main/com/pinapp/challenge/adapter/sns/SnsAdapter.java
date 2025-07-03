package com.pinapp.challenge.adapter.sns;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinapp.challenge.adapter.sns.config.SnsProperties;
import com.pinapp.challenge.domain.model.Event;
import com.pinapp.challenge.domain.port.EventPublisher;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

@Component
@AllArgsConstructor
public class SnsAdapter implements EventPublisher {

  private final SnsProperties properties;
  private final SnsClient snsClient;
  private final ObjectMapper mapper;

  public <T> void publishEvent(String type, T object) {
    try {
      Event<T> event = new Event<>(type, object);
      String json = mapper.writeValueAsString(event);

      PublishRequest request = PublishRequest.builder()
          .topicArn(properties.getTopicArn())
          .message(json)
          .build();

      snsClient.publish(request);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
