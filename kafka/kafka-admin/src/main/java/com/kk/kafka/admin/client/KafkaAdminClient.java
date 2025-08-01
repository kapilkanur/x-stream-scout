package com.kk.kafka.admin.client;

import com.kk.configuration.KafkaConfigurationData;
import com.kk.configuration.RetryConfigurationData;
import com.kk.kafka.admin.exception.KafkaClientException;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicListing;
import org.springframework.http.HttpStatus;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Component
public class KafkaAdminClient {

    private final KafkaConfigurationData kafkaConfigData;

    private final RetryConfigurationData retryConfigData;

    private final AdminClient adminClient;

    private final RetryTemplate retryTemplate;

    private final WebClient webClient;

    /**
     * Kafka admin client constructor.
     * @param config kafka configuration data
     * @param retryConfigData retry configuration data
     * @param client admin client
     * @param template retry template
     * @param webClient web client
     */
    public KafkaAdminClient(final KafkaConfigurationData config,
                            final RetryConfigurationData retryConfigData,
                            final AdminClient client,
                            final RetryTemplate template,
                            final WebClient webClient) {
        this.kafkaConfigData = config;
        this.retryConfigData = retryConfigData;
        this.adminClient = client;
        this.retryTemplate = template;
        this.webClient = webClient;
    }

    /**
     * Create topics.
     */
    public void createTopics() {
        CreateTopicsResult createTopicsResult;
        try {
            createTopicsResult = retryTemplate.execute(this::doCreateTopics);
        } catch (Throwable t) {
            throw new KafkaClientException("Reached max number of retry for creating kafka topic(s)!", t);
        }
        checkTopicsCreated();
    }

    /**
     * Check topics created.
     */
    public void checkTopicsCreated() {
        Collection<TopicListing> topics = getTopics();
        int retryCount = 1;
        Integer maxRetry = retryConfigData.getMaxAttempts();
        int multiplier = retryConfigData.getMultiplier().intValue();
        Long sleepTimeMs = retryConfigData.getSleepTimeMs();
        for (String topic : kafkaConfigData.getTopicNamesToCreate()) {
            while (!isTopicCreated(topics, topic)) {
                checkMaxRetry(retryCount++, maxRetry);
                sleep(sleepTimeMs);
                sleepTimeMs *= multiplier;
                topics = getTopics();
            }
        }
    }

    /**
     * Check the schema registry.
     */
    public void checkSchemaRegistry() {
        int retryCount = 1;
        Integer maxRetry = retryConfigData.getMaxAttempts();
        int multiplier = retryConfigData.getMultiplier().intValue();
        Long sleepTimeMs = retryConfigData.getSleepTimeMs();
        while (!getSchemaRegistryStatus().is2xxSuccessful()) {
            checkMaxRetry(retryCount++, maxRetry);
            sleep(sleepTimeMs);
            sleepTimeMs *= multiplier;
        }
    }

    private HttpStatus getSchemaRegistryStatus() {
        try {
            return webClient
                    .get()
                    .uri(kafkaConfigData.getSchemaRegistryUrl())
                    .retrieve()
                    .toBodilessEntity()
                    .map(response -> HttpStatus.valueOf(response.getStatusCode().value()))
                    .block();
        } catch (Exception e) {
            return HttpStatus.SERVICE_UNAVAILABLE;
        }
    }



    private void sleep(final Long sleepTimeMs) {
        try {
            Thread.sleep(sleepTimeMs);
        } catch (InterruptedException e) {
            throw new KafkaClientException("Error while sleeping for waiting new created topics!!");
        }
    }

    private void checkMaxRetry(final int retry, final Integer maxRetry) {
        if (retry > maxRetry) {
            throw new KafkaClientException("Reached max number of retry for reading kafka topic(s)!");
        }
    }

    private boolean isTopicCreated(final Collection<TopicListing> topics, final String topicName) {
        if (topics == null) {
            return false;
        }
        return topics.stream().anyMatch(topic -> topic.name().equals(topicName));
    }

    private CreateTopicsResult doCreateTopics(final RetryContext retryContext) {
        List<String> topicNames = kafkaConfigData.getTopicNamesToCreate();
        List<NewTopic> kafkaTopics = topicNames.stream().map(topic -> new NewTopic(
                topic.trim(),
                kafkaConfigData.getNumberOfPartitions(),
                kafkaConfigData.getReplicationFactor()
        )).collect(Collectors.toList());
        return adminClient.createTopics(kafkaTopics);
    }

    private Collection<TopicListing> getTopics() {
        Collection<TopicListing> topics;
        try {
            topics = retryTemplate.execute(this::doGetTopics);
        } catch (Throwable t) {
            throw new KafkaClientException("Reached max number of retry for reading kafka topic(s)!", t);
        }
        return topics;
    }

    private Collection<TopicListing> doGetTopics(final RetryContext retryContext)
            throws ExecutionException, InterruptedException {
        Collection<TopicListing> topics = adminClient.listTopics().listings().get();
        if (topics != null) {
            topics.forEach(topic -> System.out.println());
        }
        return topics;
    }

}
