package com.pinapp.challenge.domain.port;

import com.pinapp.challenge.domain.model.LifeExpectancy;
import java.util.Optional;

public interface LifeExpectancyRepository {
  Optional<LifeExpectancy> findById(Integer year);
}
