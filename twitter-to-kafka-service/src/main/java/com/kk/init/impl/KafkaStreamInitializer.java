package com.kk.init.impl;

import com.kk.configuration.KafkaConfigurationData;
import com.kk.init.StreamInitializer;
import com.kk.kafka.admin.client.KafkaAdminClient;
import org.springframework.stereotype.Component;

@Component
public class KafkaStreamInitializer implements StreamInitializer {

    private final KafkaConfigurationData kafkaConfigurationData;
    private final KafkaAdminClient kafkaAdminClient;

    public KafkaStreamInitializer(KafkaConfigurationData kafkaConfigurationData, KafkaAdminClient kafkaAdminClient) {
        this.kafkaConfigurationData = kafkaConfigurationData;
        this.kafkaAdminClient = kafkaAdminClient;
    }

    @Override
    public void init() {
        this.kafkaAdminClient.createTopics();
        this.kafkaAdminClient.checkSchemaRegistry();
    }
}
