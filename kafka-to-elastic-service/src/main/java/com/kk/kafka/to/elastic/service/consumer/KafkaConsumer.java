package com.kk.kafka.to.elastic.service.consumer;

import org.apache.avro.specific.SpecificRecordBase;

import java.io.Serializable;
import java.util.List;

public interface KafkaConsumer<K extends Serializable, V extends SpecificRecordBase> {
    /**
     * Receive messages.
     * @param messages
     * @param keys
     * @param partitions
     * @param offsets
     */
    void receive(List<V> messages, List<Integer> keys, List<Integer> partitions, List<Long> offsets);
}
