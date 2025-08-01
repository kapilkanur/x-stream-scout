package com.kk.kafka.producer.config;

import com.kk.configuration.KafkaConfigurationData;
import com.kk.configuration.KafkaProducerConfigurationData;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfiguration<K extends Serializable, V extends SpecificRecordBase> {

    private final KafkaConfigurationData kafkaConfigurationData;
    private final KafkaProducerConfigurationData kafkaProducerConfigurationData;

    /**
     * KafkaProducerConfiguration constructor.
     * @param kafkaConfigurationData KafkaConfigurationData
     * @param kafkaProducerConfigurationData KafkaProducerConfigurationData
     */
    public KafkaProducerConfiguration(final KafkaConfigurationData kafkaConfigurationData, final KafkaProducerConfigurationData kafkaProducerConfigurationData) {
        this.kafkaConfigurationData = kafkaConfigurationData;
        this.kafkaProducerConfigurationData = kafkaProducerConfigurationData;
    }

    /**
     * Create producer configuration.
     * @return producer configuration
     */
    @Bean
    public Map<String, Object> producerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfigurationData.getBootstrapServers());
        props.put(kafkaConfigurationData.getSchemaRegistryUrlKey(), kafkaConfigurationData.getSchemaRegistryUrl());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaProducerConfigurationData.getKeySerializerClass());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaProducerConfigurationData.getValueSerializerClass());
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, kafkaProducerConfigurationData.getBatchSize()
                * kafkaProducerConfigurationData.getBatchSizeBoostFactor());
        props.put(ProducerConfig.LINGER_MS_CONFIG, kafkaProducerConfigurationData.getLingerMs());
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, kafkaProducerConfigurationData.getCompressionType());
        props.put(ProducerConfig.ACKS_CONFIG, kafkaProducerConfigurationData.getAcks());
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, kafkaProducerConfigurationData.getRequestTimeoutMs());
        props.put(ProducerConfig.RETRIES_CONFIG, kafkaProducerConfigurationData.getRetryCount());
        return props;
    }

    /**
     * Set ProducerFactory.
     * @return ProducerFactory<K, V>
     */
    @Bean
    public ProducerFactory<K, V> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    /**
     * Set KafkaTemplate.
     * @return KafkaTemplate<K, V>
     */
    @Bean
    public KafkaTemplate<K, V> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
