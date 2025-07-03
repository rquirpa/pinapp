package com.pinapp.challenge.adapter.postgres.jpa;

import com.pinapp.challenge.adapter.postgres.entity.LifeExpectancyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LifeExpectancyJpaRepository extends JpaRepository<LifeExpectancyEntity, Integer>  {

}
