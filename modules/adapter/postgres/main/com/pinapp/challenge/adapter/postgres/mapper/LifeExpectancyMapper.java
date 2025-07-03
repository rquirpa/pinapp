package com.pinapp.challenge.adapter.postgres.mapper;

import com.pinapp.challenge.adapter.postgres.entity.LifeExpectancyEntity;
import com.pinapp.challenge.domain.model.LifeExpectancy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface LifeExpectancyMapper {
  LifeExpectancyEntity map(LifeExpectancy dto);
  LifeExpectancy map(LifeExpectancyEntity entity);
}
