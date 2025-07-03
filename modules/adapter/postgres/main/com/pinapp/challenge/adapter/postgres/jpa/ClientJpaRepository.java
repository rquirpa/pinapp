package com.pinapp.challenge.adapter.postgres.jpa;

import com.pinapp.challenge.adapter.postgres.entity.ClientEntity;
import com.pinapp.challenge.adapter.postgres.projection.ClientAgeStatsProjection;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientJpaRepository extends JpaRepository<ClientEntity, UUID> {

  @Query(value = """
    SELECT
      AVG(EXTRACT(YEAR FROM AGE(CURRENT_DATE, birth_date))) AS averageAge,
      STDDEV_POP(EXTRACT(YEAR FROM AGE(CURRENT_DATE, birth_date))) AS standardDeviationAge
    FROM clients
    """, nativeQuery = true)
  ClientAgeStatsProjection getClientAgeStats();
}
