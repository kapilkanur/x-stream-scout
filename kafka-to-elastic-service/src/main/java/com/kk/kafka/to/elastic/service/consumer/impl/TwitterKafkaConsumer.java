package com.kk.kafka.to.elastic.service.consumer.impl;

import com.kk.avro.TweetAvroModel;
import com.kk.configuration.KafkaConfigurationData;
import com.kk.configuration.KafkaConsumerConfigurationData;
import com.kk.elastic.index.client.service.ElasticIndexClient;
import com.kk.elastic.model.index.impl.TwitterIndexModel;
import com.kk.kafka.admin.client.KafkaAdminClient;
import com.kk.kafka.to.elastic.service.consumer.KafkaConsumer;
import com.kk.kafka.to.elastic.service.transformer.AvroToElasticModelTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TwitterKafkaConsumer implements KafkaConsumer<Long, TweetAvroModel> {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterKafkaConsumer.class);

    private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    private final KafkaAdminClient kafkaAdminClient;

    private final KafkaConfigurationData kafkaConfigData;

    private final AvroToElasticModelTransformer avroToElasticModelTransformer;

    private final KafkaConsumerConfigurationData kafkaConsumerConfigurationData;

    private final ElasticIndexClient<TwitterIndexModel> elasticIndexClient;

    /**
     * TwitterKafkaConsumer constructor.
     * @param listenerEndpointRegistry
     * @param adminClient
     * @param configData
     * @param avroToElasticModelTransformer
     * @param kafkaConsumerConfigurationData
     * @param elasticIndexClient
     */
    public TwitterKafkaConsumer(final KafkaListenerEndpointRegistry listenerEndpointRegistry,
                                final KafkaAdminClient adminClient,
                                final KafkaConfigurationData configData,
                                final AvroToElasticModelTransformer avroToElasticModelTransformer,
                                final KafkaConsumerConfigurationData kafkaConsumerConfigurationData,
                                final ElasticIndexClient<TwitterIndexModel> elasticIndexClient) {
        this.kafkaListenerEndpointRegistry = listenerEndpointRegistry;
        this.kafkaAdminClient = adminClient;
        this.kafkaConfigData = configData;
        this.avroToElasticModelTransformer = avroToElasticModelTransformer;
        this.kafkaConsumerConfigurationData = kafkaConsumerConfigurationData;
        this.elasticIndexClient = elasticIndexClient;
    }

    /**
     * On application start listenener.
     * @param applicationStartedEvent
     */
    @EventListener
    public void onAppStart(final ApplicationStartedEvent applicationStartedEvent) {
        this.kafkaAdminClient.checkTopicsCreated();
        LOG.info("Topics with name {} is ready for consumption!", this.kafkaConfigData.getTopicNamesToCreate().toArray());
        Objects.requireNonNull(this.kafkaListenerEndpointRegistry.getListenerContainer(this.kafkaConsumerConfigurationData.getConsumerGroupId())).start();
    }

    /**
     * Receive messages.
     * @param messages
     * @param keys
     * @param partitions
     * @param offsets
     */
    @Override
    @KafkaListener(id = "${kafka-consumer-config.consumer-group-id}", topics = "${kafka-config.topic-name}")
    public void receive(@Payload final List<TweetAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) final List<Integer> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) final List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) final List<Long> offsets) {
        LOG.info("{} number of message received with keys {}, partitions {} and offsets {}, "
                        + "sending it to elastic: Thread id {}",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString(),
                Thread.currentThread().getId());
        List<TwitterIndexModel> twitterIndexModels = this.avroToElasticModelTransformer.getElasticModels(messages);
        List<String> documentIds = this.elasticIndexClient.save(twitterIndexModels);
        LOG.info("Documents saved to elasticsearch with ids {}", documentIds.toArray());
    }
}

