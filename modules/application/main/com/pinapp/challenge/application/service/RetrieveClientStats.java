package com.pinapp.challenge.application.service;

import com.pinapp.challenge.domain.model.ClientStats;
import com.pinapp.challenge.domain.port.ClientStatsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RetrieveClientStats {

  private final ClientStatsRepository clientStatsRepository;

  public ClientStats execute() {
    return clientStatsRepository.find();
  }
}
