package com.pinapp.challenge.adapter.postgres.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "life_expectancy")
@Getter
@Setter
public class LifeExpectancyEntity {

  @Id
  @Column(updatable = false, nullable = false)
  private Integer bornYear;

  @Column(nullable = false)
  private Double expectancyFemale;

  @Column(nullable = false)
  private Double expectancyMale;

  @Column(nullable = false)
  private Double expectancyOverall;
}
