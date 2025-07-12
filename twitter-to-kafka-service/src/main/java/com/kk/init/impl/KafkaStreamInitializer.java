package com.kk.init.impl;

import com.kk.configuration.KafkaConfigurationData;
import com.kk.init.StreamInitializer;
import com.kk.kafka.admin.client.KafkaAdminClient;
import org.springframework.stereotype.Component;

@Component
public class KafkaStreamInitializer implements StreamInitializer {

    private final KafkaConfigurationData kafkaConfigurationData;
    private final KafkaAdminClient kafkaAdminClient;

    /**
     * KafkaStreamInitializer constructor.
     * @param kafkaConfigurationData KafkaConfigurationData
     * @param kafkaAdminClient KafkaAdminClient
     */
    public KafkaStreamInitializer(final KafkaConfigurationData kafkaConfigurationData, final KafkaAdminClient kafkaAdminClient) {
        this.kafkaConfigurationData = kafkaConfigurationData;
        this.kafkaAdminClient = kafkaAdminClient;
    }

    /**
     * Initialization method.
     */
    @Override
    public void init() {
        this.kafkaAdminClient.createTopics();
        this.kafkaAdminClient.checkSchemaRegistry();
    }
}
