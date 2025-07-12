package com.kk;

import com.kk.configuration.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TwitterToKafkaServiceApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterToKafkaServiceApplication.class);

    private final ApplicationProperties properties;

    /**
     * TwitterToKafkaServiceApplication constructor.
     * @param properties
     */
    public TwitterToKafkaServiceApplication(final ApplicationProperties properties) {
        this.properties = properties;
    }

    /**
     * Main method.
     * @param args
     */
    public static void main(final String[] args) {
        SpringApplication.run(TwitterToKafkaServiceApplication.class, args);
    }

    /**
     * Run method.
     * @param args
     */
    @Override
    public void run(final String... args) {
        LOG.info("Application started.");
        LOG.info("Keywords: {}", properties.getTwitterKeywords());
        LOG.info("Welcome Message: {}", properties.getWelcomeMessage());
    }
}
