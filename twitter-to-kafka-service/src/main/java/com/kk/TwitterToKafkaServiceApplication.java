package com.kk;

import com.kk.configurations.ApplicationConfiguration;
import com.kk.configurations.TwitterKeywordsData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TwitterToKafkaServiceApplication implements CommandLineRunner {

    private final TwitterKeywordsData twitterKeywordsData;
    private final ApplicationConfiguration applicationConfiguration;
    private static final Logger LOG = LoggerFactory.getLogger(TwitterToKafkaServiceApplication.class);

    public TwitterToKafkaServiceApplication(TwitterKeywordsData twitterKeywordsData, ApplicationConfiguration applicationConfiguration) {
        this.twitterKeywordsData = twitterKeywordsData;
        this.applicationConfiguration = applicationConfiguration;
    }


    public static void main(String[] args) {
        SpringApplication.run(TwitterToKafkaServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        LOG.info("Application started.");
        LOG.info(twitterKeywordsData.getTwitterKeywords().toString());
        LOG.info(applicationConfiguration.getWelcomeMessage());
    }
}