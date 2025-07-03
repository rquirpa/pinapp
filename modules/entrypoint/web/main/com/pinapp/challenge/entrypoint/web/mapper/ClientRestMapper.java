package com.pinapp.challenge.entrypoint.web.mapper;

import com.pinapp.challenge.domain.model.Client;
import com.pinapp.challenge.entrypoint.web.controller.ClientRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface ClientRestMapper {
  Client map(ClientRequest request);
}
