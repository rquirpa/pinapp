package com.pinapp.challenge.adapter.postgres.jpa;

import com.pinapp.challenge.adapter.postgres.entity.ClientStatsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientStatsJpaRepository extends JpaRepository<ClientStatsEntity, Integer>  {

}
