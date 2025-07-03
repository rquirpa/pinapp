package com.pinapp.challenge.entrypoint.web.config;

import com.pinapp.challenge.application.exception.ApplicationException;
import com.pinapp.challenge.domain.exception.DomainException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({
      DomainException.class,
      ApplicationException.class
  })
  public ResponseEntity<ErrorResponse> handleBadRequest(RuntimeException ex, WebRequest request) {
    ErrorResponse error = new ErrorResponse(
        HttpStatus.BAD_REQUEST,
        ex.getMessage(),
        request.getDescription(false)
    );

    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, WebRequest request) {
    ErrorResponse error = new ErrorResponse(
        HttpStatus.INTERNAL_SERVER_ERROR,
        ex.getMessage(),
        request.getDescription(false)
    );

    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
    List<String> errors = ex.getBindingResult().getFieldErrors().stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.toList());

    ErrorResponse error = new ErrorResponse(
        HttpStatus.BAD_REQUEST,
        errors,
        request.getDescription(false)
    );

    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }
}
