package com.pinapp.challenge.domain.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Client {

  private UUID id;
  private String firstName;
  private String lastName;
  private Sex sex;
  private LocalDate birthDate;
  private LocalDate estimatedDeathDate;

  public Integer getAge() {
    if (this.birthDate != null) {
      return Period.between(this.birthDate, LocalDate.now()).getYears();
    }
    return null;
  }
}
