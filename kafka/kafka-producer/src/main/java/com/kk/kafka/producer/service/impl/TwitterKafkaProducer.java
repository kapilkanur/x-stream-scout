package com.kk.kafka.producer.service.impl;

import com.kk.avro.TweetAvroModel;
import com.kk.kafka.producer.service.KafkaProducer;
import jakarta.annotation.PreDestroy;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TwitterKafkaProducer implements KafkaProducer<Long, TweetAvroModel> {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterKafkaProducer.class);

    private final KafkaTemplate<Long, TweetAvroModel> kafkaTemplate;

    /**
     * TwitterKafkaProducer constructor.
     * @param kafkaTemplate KafkaTemplate<Long, TweetAvroModel>
     */
    public TwitterKafkaProducer(final KafkaTemplate<Long, TweetAvroModel> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Send message.
     * @param topicName Topic name
     * @param key key
     * @param message message
     */
    @Override
    public void send(final String topicName, final Long key, final TweetAvroModel message) {
        LOG.info("Sending message='{}' to topic='{}'", message, topicName);

        kafkaTemplate.send(topicName, key, message)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        LOG.error("Error while sending message={} to topic={} with key={}", message, topicName, key, ex);
                    } else {
                        RecordMetadata metadata = result.getRecordMetadata();
                        LOG.info("Message sent successfully to topic={}, partition={}, offset={}, timestamp={}",
                                metadata.topic(), metadata.partition(), metadata.offset(), metadata.timestamp());
                    }
                });
    }

    /**
     * Close kafka producer.
     */
    @PreDestroy
    public void close() {
        LOG.info("Closing Kafka producer!");
        kafkaTemplate.destroy();
    }
}
