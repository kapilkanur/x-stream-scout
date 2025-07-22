package com.kk.kafka.consumer.configuration;

import com.kk.configuration.KafkaConfigurationData;
import com.kk.configuration.KafkaConsumerConfigurationData;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfiguration<K extends Serializable, V extends SpecificRecordBase> {

    private KafkaConfigurationData kafkaConfigData;

    private KafkaConsumerConfigurationData kafkaConsumerConfigData;

    /**
     * KafkaConsumerConfig constructor.
     * @param configData
     * @param consumerConfigData
     */
    public KafkaConsumerConfiguration(final KafkaConfigurationData configData, final KafkaConsumerConfigurationData consumerConfigData) {
        this.kafkaConfigData = configData;
        this.kafkaConsumerConfigData = consumerConfigData;
    }

    /**
     * Consumer configs.
     * @return Map<String, Object>
     */
    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.kafkaConfigData.getBootstrapServers());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, this.kafkaConsumerConfigData.getKeyDeserializer());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, this.kafkaConsumerConfigData.getValueDeserializer());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, this.kafkaConsumerConfigData.getConsumerGroupId());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, this.kafkaConsumerConfigData.getAutoOffsetReset());
        props.put(kafkaConfigData.getSchemaRegistryUrlKey(), this.kafkaConfigData.getSchemaRegistryUrl());
        props.put(kafkaConsumerConfigData.getSpecificAvroReaderKey(), this.kafkaConsumerConfigData.getSpecificAvroReader());
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, this.kafkaConsumerConfigData.getSessionTimeoutMs());
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, this.kafkaConsumerConfigData.getHeartbeatIntervalMs());
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, this.kafkaConsumerConfigData.getMaxPollIntervalMs());
        props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG,
                this.kafkaConsumerConfigData.getMaxPartitionFetchBytesDefault()
                        * this.kafkaConsumerConfigData.getMaxPartitionFetchBytesBoostFactor());
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, this.kafkaConsumerConfigData.getMaxPollRecords());
        return props;
    }

    /**
     * Consumer factory.
     * @return ConsumerFactory<K, V>
     */
    @Bean
    public ConsumerFactory<K, V> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    /**
     * KafkaListenerContainerFactory.
     * @return KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<K, V>>
     */
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<K, V>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<K, V> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setBatchListener(kafkaConsumerConfigData.getBatchListener());
        factory.setConcurrency(kafkaConsumerConfigData.getConcurrencyLevel());
        factory.setAutoStartup(kafkaConsumerConfigData.getAutoStartup());
        factory.getContainerProperties().setPollTimeout(kafkaConsumerConfigData.getPollTimeoutMs());
        return factory;


    }
}
