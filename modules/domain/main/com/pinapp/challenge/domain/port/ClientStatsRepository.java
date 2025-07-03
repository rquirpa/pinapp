package com.pinapp.challenge.domain.port;

import com.pinapp.challenge.domain.model.ClientStats;

public interface ClientStatsRepository {
  ClientStats save(ClientStats client);
  ClientStats find();
}
