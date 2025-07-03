package com.pinapp.challenge.entrypoint.web.controller;

import com.pinapp.challenge.entrypoint.web.config.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Tag(name = "Auth", description = "Operaciones para autenticación en el API")
public class AuthController {

  private final JwtService jwtService;

  @Operation(
      summary = "Iniciar sesión",
      description = "Valida las credenciales y devuelve un token JWT si son correctas."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Autenticación exitosa",
          content = @Content(schema = @Schema(implementation = AuthResponse.class))),
      @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
  })
  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest body) {
    if ("admin".equals(body.username()) && "123456".equals(body.password())) {
      String token = jwtService.generateToken(body.username());
      return ResponseEntity.ok(new AuthResponse(token));
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }
}
