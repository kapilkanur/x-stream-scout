package com.kk.kafka.admin.exception;

public class KafkaClientException extends RuntimeException {

    /**
     * KafkaClientException constructor.
     */
    public KafkaClientException() {
    }

    /**
     * Kafka client exception with message.
     * @param message message
     */
    public KafkaClientException(final String message) {
        super(message);
    }

    /**
     * Kafka client exception with message and cause.
     * @param message message
     * @param cause cause
     */
    public KafkaClientException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
