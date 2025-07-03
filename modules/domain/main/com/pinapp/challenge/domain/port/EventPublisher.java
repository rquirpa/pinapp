package com.pinapp.challenge.domain.port;

public interface EventPublisher {
  <T> void publishEvent(String type, T object);
}
