package com.pinapp.challenge.adapter.postgres.mapper;

import com.pinapp.challenge.adapter.postgres.entity.ClientStatsEntity;
import com.pinapp.challenge.domain.model.ClientStats;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface ClientStatsMapper {
  ClientStatsEntity map(ClientStats dto);
  ClientStats map(ClientStatsEntity entity);
}
