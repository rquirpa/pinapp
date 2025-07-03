package com.pinapp.challenge.domain.model;

import java.util.UUID;
import lombok.Getter;

@Getter
public class Event<T> {

  private final UUID id;
  private final String type;
  private final T payload;

  public Event(String type, T payload) {
    this.id = UUID.randomUUID();
    this.type = type;
    this.payload = payload;
  }
}
