package com.pinapp.challenge.entrypoint.web.controller;

import com.pinapp.challenge.application.service.CreateClient;
import com.pinapp.challenge.application.service.RetrieveClientStats;
import com.pinapp.challenge.application.service.RetrieveClients;
import com.pinapp.challenge.domain.model.Client;
import com.pinapp.challenge.domain.model.ClientStats;
import com.pinapp.challenge.entrypoint.web.config.ErrorResponse;
import com.pinapp.challenge.entrypoint.web.mapper.ClientRestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/clients")
@Tag(name = "Clients", description = "Operaciones relacionadas con clientes")
public class ClientController {

  private final CreateClient createClient;
  private final RetrieveClients retrieveClients;
  private final RetrieveClientStats retrieveClientStats;
  private final ClientRestMapper mapper;

  @Operation(
      security = @SecurityRequirement(name = "bearerAuth"),
      summary = "Crear un nuevo cliente",
      description = "Crea un nuevo cliente a partir de los datos enviados en el cuerpo de la solicitud."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente",
          content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "400", description = "Solicitud inválida (errores de validación)",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "401", description = "No autorizado",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PostMapping
  public ResponseEntity<Client> create(@Valid @RequestBody ClientRequest request) {
    Client client = mapper.map(request);
    Client createdClient = createClient.execute(client);

    return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
  }

  @Operation(
      security = @SecurityRequirement(name = "bearerAuth"),
      summary = "Listar clientes",
      description = "Obtiene una lista paginada de clientes. Soporta parámetros como `page`, `size` y `sort`."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Listado exitoso de clientes",
          content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "401", description = "No autorizado",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
  })
  @GetMapping
  public ResponseEntity<Page<Client>> retrieve(Pageable pageable) {
    Page<Client> clients = retrieveClients.execute(pageable);

    return ResponseEntity.status(HttpStatus.OK).body(clients);
  }

  @Operation(
      security = @SecurityRequirement(name = "bearerAuth"),
      summary = "Obtener estadísticas de edad de clientes",
      description = "Calcula y devuelve estadísticas como la edad promedio y la desviación estándar de todos los clientes."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Estadísticas obtenidas correctamente",
          content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "401", description = "No autorizado",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
  })
  @GetMapping(path = "/stats")
  public ResponseEntity<ClientStats> retrieveStats() {
    ClientStats clientStats = retrieveClientStats.execute();

    return ResponseEntity.status(HttpStatus.OK).body(clientStats);
  }
}
