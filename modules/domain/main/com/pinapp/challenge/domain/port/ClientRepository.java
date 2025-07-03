package com.pinapp.challenge.domain.port;

import com.pinapp.challenge.domain.model.Client;
import com.pinapp.challenge.domain.model.ClientStats;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientRepository {
  Client save(Client client);
  Page<Client> findAll(Pageable pageable);
  ClientStats getClientAgeStats();
}
