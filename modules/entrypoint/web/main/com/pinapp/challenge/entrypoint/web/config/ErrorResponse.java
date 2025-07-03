package com.pinapp.challenge.entrypoint.web.config;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class ErrorResponse{
  private final LocalDateTime timestamp;
  private final Integer status;
  private final String error;
  private final List<String> messages;
  private final String path;

  public ErrorResponse(HttpStatus httpStatus, String message, String path) {
    this.timestamp = LocalDateTime.now();
    this.status = httpStatus.value();
    this.error = httpStatus.getReasonPhrase();
    this.messages = List.of(message);
    this.path = path;
  }

  public ErrorResponse(HttpStatus httpStatus, List<String> messages, String path) {
    this.timestamp = LocalDateTime.now();
    this.status = httpStatus.value();
    this.error = httpStatus.getReasonPhrase();
    this.messages = messages;
    this.path = path;
  }
}
