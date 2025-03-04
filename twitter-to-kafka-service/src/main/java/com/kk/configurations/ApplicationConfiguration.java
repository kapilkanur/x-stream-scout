package com.kk.configurations;

import com.kk.services.TweetsSource;
import com.kk.services.impl.InAppTweetsSimulatorImpl;
import com.kk.services.impl.XstreamSimulatorImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "twitter-to-kafka-service")
public class ApplicationConfiguration {

    private String welcomeMessage;

    @Value("${tweets.source}")
    private String tweetsSource;

    @Bean
    public TweetsSource tweetsSource() {
        if ("external".equalsIgnoreCase(tweetsSource)) {
            return new XstreamSimulatorImpl();
        }
        return new InAppTweetsSimulatorImpl();
    }

}