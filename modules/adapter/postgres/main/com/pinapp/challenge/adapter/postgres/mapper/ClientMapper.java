package com.pinapp.challenge.adapter.postgres.mapper;

import com.pinapp.challenge.adapter.postgres.entity.ClientEntity;
import com.pinapp.challenge.domain.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface ClientMapper {
  ClientEntity map(Client dto);
  Client map(ClientEntity entity);
}
