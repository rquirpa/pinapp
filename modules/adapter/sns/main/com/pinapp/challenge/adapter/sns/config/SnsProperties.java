package com.pinapp.challenge.adapter.sns.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sns")
@Getter
@Setter
public class SnsProperties {
  private String topicArn;
}
