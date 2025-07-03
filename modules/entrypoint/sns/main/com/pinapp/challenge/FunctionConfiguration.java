package com.pinapp.challenge;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinapp.challenge.entrypoint.sns.EventHandler;
import java.util.List;
import java.util.function.Consumer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FunctionConfiguration {

  public static void main(String[] args) {}

  @Bean
  public Consumer<SNSEvent> eventListener(ObjectMapper mapper, List<EventHandler> eventHandlers) {
    return event -> {
      event.getRecords().forEach(record -> {
        try {
          String message = record.getSNS().getMessage();
          JsonNode root = mapper.readTree(message);
          String type = root.get("type").asText();
          JsonNode payload = root.get("payload");

          for (EventHandler eventHandler : eventHandlers) {
            if (type.equals(eventHandler.getType())) {
              eventHandler.process(payload);
            }
          }
        } catch (JsonProcessingException e) {
          throw new RuntimeException(e);
        }
      });
    };
  }
}
