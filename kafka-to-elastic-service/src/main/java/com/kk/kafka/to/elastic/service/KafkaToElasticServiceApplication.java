package com.kk.kafka.to.elastic.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.kk")
public class KafkaToElasticServiceApplication {
    /**
     * Main method.
     * @param args
     */
    public static void main(final String[] args) {
        SpringApplication.run(KafkaToElasticServiceApplication.class, args);
    }
}
