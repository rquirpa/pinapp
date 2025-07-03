package com.pinapp.challenge.application.service;

import com.pinapp.challenge.domain.model.Client;
import com.pinapp.challenge.domain.port.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RetrieveClients {

  private final ClientRepository repository;

  public Page<Client> execute(Pageable pageable) {
    return repository.findAll(pageable);
  }
}
