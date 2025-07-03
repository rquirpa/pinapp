package com.pinapp.challenge.application.service;

import com.pinapp.challenge.application.exception.ApplicationException;
import com.pinapp.challenge.domain.model.Client;
import com.pinapp.challenge.domain.model.LifeExpectancy;
import com.pinapp.challenge.domain.port.ClientRepository;
import com.pinapp.challenge.domain.port.EventPublisher;
import com.pinapp.challenge.domain.port.LifeExpectancyRepository;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateClient {

  private final ClientRepository clientRepository;
  private final LifeExpectancyRepository lifeExpectancyRepository;
  private final EventPublisher publisher;

  public Client execute(Client client) {
    LifeExpectancy expectancy = lifeExpectancyRepository
        .findById(client.getBirthDate().getYear())
        .orElseThrow(() -> new ApplicationException("No se encontró expectativa de vida"));

    double expectancyYears = switch (client.getSex()) {
      case MALE -> expectancy.expectancyMale();
      case FEMALE -> expectancy.expectancyFemale();
    };

    LocalDate estimatedDeathDate = client.getBirthDate().plusDays((long) (expectancyYears * 365.25));

    client.setEstimatedDeathDate(estimatedDeathDate);

    Client savedClient = clientRepository.save(client);
    publisher.publishEvent("client:created", savedClient);

    return savedClient;
  }
}
