package com.ecom.integration;

import java.time.Duration;
import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "app")
@EnableConfigurationProperties
@Configuration
@Data
public class AppConfigProperties {

    private Map<String, String> clients;

    private Retry retry;

    @Data
    public static final class Retry {

        private int maxTimes;

        private Duration maxDuration;

    }
}
