package com.pinapp.challenge.adapter.sns.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

@Configuration
@Profile("prod")
public class ProdSnsClientConfig {

  @Value("${aws.sns.region:us-east-1}")
  private String region;

  @Bean
  public SnsClient snsClient() {
    return SnsClient.builder()
        .region(Region.of(region))
        .build();
  }
}
