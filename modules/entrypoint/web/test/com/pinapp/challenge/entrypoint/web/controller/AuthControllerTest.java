package com.pinapp.challenge.entrypoint.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pinapp.challenge.entrypoint.web.config.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class AuthControllerTest {

  @Mock
  private JwtService jwtService;

  @InjectMocks
  private AuthController authController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void whenCredentialsAreValid_thenReturnToken() {
    // Arrange
    AuthRequest request = new AuthRequest("admin", "123456");
    String expectedToken = "jwt-token";
    when(jwtService.generateToken("admin")).thenReturn(expectedToken);

    // Act
    ResponseEntity<AuthResponse> response = authController.login(request);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(expectedToken, response.getBody().token());

    verify(jwtService, times(1)).generateToken("admin");
  }

  @Test
  void whenUserIsInvalid_ThenReturn401() {
    // Arrange
    AuthRequest request = new AuthRequest("user", "123456");

    // Act
    ResponseEntity<AuthResponse> response = authController.login(request);

    // Assert
    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    assertNull(response.getBody());

    verify(jwtService, never()).generateToken(anyString());
  }

  @Test
  void whenPasswordIsInvalid_ThenReturn401() {
    // Arrange
    AuthRequest request = new AuthRequest("admin", "wrongpassword");

    // Act
    ResponseEntity<AuthResponse> response = authController.login(request);

    // Assert
    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    assertNull(response.getBody());

    verify(jwtService, never()).generateToken(anyString());
  }
}
