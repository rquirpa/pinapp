package com.pinapp.challenge.entrypoint.sns;


import com.fasterxml.jackson.databind.JsonNode;
import com.pinapp.challenge.application.service.UpdateClientStats;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ClientCreatedEventHandler implements EventHandler {

  private final UpdateClientStats updateClientStats;

  @Override
  public String getType() {
    return "client:created";
  }

  @Override
  public void process(JsonNode message) {
    updateClientStats.execute();
  }
}
