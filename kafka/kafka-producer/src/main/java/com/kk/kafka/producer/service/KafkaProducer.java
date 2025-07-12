package com.kk.kafka.producer.service;

import org.apache.avro.specific.SpecificRecordBase;

import java.io.Serializable;

public interface KafkaProducer<K extends Serializable, V extends SpecificRecordBase> {
    /**
     * Send message.
     * @param topicName
     * @param key
     * @param message
     */
    void send(String topicName, K key, V message);
}
