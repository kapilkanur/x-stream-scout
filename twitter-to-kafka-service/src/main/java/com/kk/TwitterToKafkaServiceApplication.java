package com.kk;

import com.kk.configuration.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TwitterToKafkaServiceApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterToKafkaServiceApplication.class);

    private final ApplicationProperties properties;

    public TwitterToKafkaServiceApplication(ApplicationProperties properties) {
        this.properties = properties;
    }

    public static void main(String[] args) {
        SpringApplication.run(TwitterToKafkaServiceApplication.class, args);
    }

    @Override
    public void run(String... args) {
        LOG.info("Application started.");
        LOG.info("Keywords: {}", properties.getTwitterKeywords());
        LOG.info("Welcome Message: {}", properties.getWelcomeMessage());
    }
}
