package com.kk.kafka.admin.config;

import com.kk.configuration.KafkaConfigurationData;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

import java.util.Map;

@EnableRetry
@Configuration
public class KafkaAdminConfiguration {

    private final KafkaConfigurationData kafkaConfigurationData;

    public KafkaAdminConfiguration(KafkaConfigurationData kafkaConfigurationData) {
        this.kafkaConfigurationData = kafkaConfigurationData;
    }

    @Bean
    public AdminClient adminClient() {
        return AdminClient.create(Map.of(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG,
                kafkaConfigurationData.getBootstrapServers()));
    }



}
