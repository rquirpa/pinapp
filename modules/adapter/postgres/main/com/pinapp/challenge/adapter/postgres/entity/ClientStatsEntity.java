package com.pinapp.challenge.adapter.postgres.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "client_statistics")
@Getter
@Setter
public class ClientStatsEntity {
  @Id
  private Integer id = 0;

  @Column(name = "average_age")
  private Double averageAge;

  @Column(name = "standard_deviation_age")
  private Double standardDeviationAge;
}
