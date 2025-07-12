package com.kk.common;

import com.kk.configuration.RetryConfigurationData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class RetryConfiguration {

    private final RetryConfigurationData retryConfigurationData;

    /**
     * Constructor of RetryConfiguration.
     * @param retryConfigurationData Retry configuration data
     */
    public RetryConfiguration(final RetryConfigurationData retryConfigurationData) {
        this.retryConfigurationData = retryConfigurationData;
    }

    /**
     * Create RetryTemplate.
     * @return RetryTemplate
     */
    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        ExponentialBackOffPolicy exponentialBackOffPolicy = new ExponentialBackOffPolicy();
        exponentialBackOffPolicy.setInitialInterval(this.retryConfigurationData.getInitialIntervalMs());
        exponentialBackOffPolicy.setMaxInterval(this.retryConfigurationData.getMaxIntervalMs());
        exponentialBackOffPolicy.setMultiplier(this.retryConfigurationData.getMultiplier());

        retryTemplate.setBackOffPolicy(exponentialBackOffPolicy);

        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy();
        simpleRetryPolicy.setMaxAttempts(this.retryConfigurationData.getMaxAttempts());

        retryTemplate.setRetryPolicy(simpleRetryPolicy);

        return retryTemplate;
    }


}
