package com.pinapp.challenge.adapter.postgres.entity;

import com.pinapp.challenge.domain.model.Sex;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "clients")
@Getter
@Setter
public class ClientEntity {

  @Id
  @GeneratedValue(generator = "uuid")
  @Column(columnDefinition = "uuid", updatable = false, nullable = false)
  private UUID id;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Sex sex;

  @Column(nullable = false)
  private LocalDate birthDate;

  @Column(nullable = false)
  private LocalDate estimatedDeathDate;
}
