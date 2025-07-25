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

    /**
     * KafkaAdminConfiguration constructor.
     * @param kafkaConfigurationData KafkaAdminConfiguration
     */
    public KafkaAdminConfiguration(final KafkaConfigurationData kafkaConfigurationData) {
        this.kafkaConfigurationData = kafkaConfigurationData;
    }

    /**
     * Create AdminClient.
     * @return AdminClient
     */
    @Bean
    public AdminClient adminClient() {
        return AdminClient.create(Map.of(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG,
                kafkaConfigurationData.getBootstrapServers()));
    }



}
