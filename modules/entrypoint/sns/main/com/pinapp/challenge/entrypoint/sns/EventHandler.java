package com.pinapp.challenge.entrypoint.sns;

import com.fasterxml.jackson.databind.JsonNode;

public interface EventHandler {
  String getType();
  void process(JsonNode message);
}
