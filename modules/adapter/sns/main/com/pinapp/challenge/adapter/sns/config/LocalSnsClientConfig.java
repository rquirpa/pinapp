package com.pinapp.challenge.adapter.sns.config;

import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

@Configuration
@Profile("local")
public class LocalSnsClientConfig {

  @Value("${aws.sns.region:us-east-1}")
  private String region;

  @Value("${aws.sns.endpoint:http://localstack:4566}")
  private String endpoint;

  @Value("${aws.sns.access-key:test}")
  private String accessKey;

  @Value("${aws.sns.secret-key:test}")
  private String secretKey;

  @Bean
  public SnsClient snsClient() {
    return SnsClient.builder()
        .region(Region.of(region))
        .endpointOverride(URI.create(endpoint))
        .credentialsProvider(StaticCredentialsProvider.create(
            AwsBasicCredentials.create(accessKey, secretKey)
        ))
        .build();
  }
}
