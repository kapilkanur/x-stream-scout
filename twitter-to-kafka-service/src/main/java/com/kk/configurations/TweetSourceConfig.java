package com.kk.configurations;

import com.kk.configuration.ApplicationProperties;
import com.kk.services.TweetsSource;
import com.kk.services.impl.InAppTweetsSimulatorImpl;
import com.kk.services.impl.XstreamSimulatorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TweetSourceConfig {

    private final ApplicationProperties properties;

    public TweetSourceConfig(ApplicationProperties properties) {
        this.properties = properties;
    }

    @Bean
    public TweetsSource tweetsSource() {
        return "external".equalsIgnoreCase(properties.getTweets().getSource())
                ? new XstreamSimulatorImpl()
                : new InAppTweetsSimulatorImpl();
    }
}
