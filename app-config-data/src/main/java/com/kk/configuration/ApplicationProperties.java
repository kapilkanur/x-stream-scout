package com.kk.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "twitter-to-kafka-service")
public class ApplicationProperties {
    private String welcomeMessage;
    private Tweets tweets;
    private List<String> twitterKeywords;

    @Data
    public static class Tweets {
        private String source;
    }
}

