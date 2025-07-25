package com.kk.kafka.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

    /**
     * WebClient Constructor.
     * @return WebClient
     */
    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

}
