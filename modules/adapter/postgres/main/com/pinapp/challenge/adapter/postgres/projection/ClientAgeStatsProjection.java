package com.pinapp.challenge.adapter.postgres.projection;

public interface ClientAgeStatsProjection {
  Double getAverageAge();
  Double getStandardDeviationAge();
}
