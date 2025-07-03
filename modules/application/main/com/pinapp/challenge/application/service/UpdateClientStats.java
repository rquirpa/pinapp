package com.pinapp.challenge.application.service;

import com.pinapp.challenge.domain.model.ClientStats;
import com.pinapp.challenge.domain.port.ClientRepository;
import com.pinapp.challenge.domain.port.ClientStatsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateClientStats {

  private final ClientRepository clientRepository;
  private final ClientStatsRepository clientStatsRepository;

  public void execute() {
    ClientStats clientStats = clientRepository.getClientAgeStats();
    clientStatsRepository.save(clientStats);
  }
}
