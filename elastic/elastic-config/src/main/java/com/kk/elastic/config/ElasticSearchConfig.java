package com.kk.elastic.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import com.kk.configuration.ElasticConfigData;
import jakarta.annotation.PreDestroy;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Objects;

@Configuration
public class ElasticSearchConfig {

    private final ElasticConfigData elasticConfigData;
    private RestClient restClient;

    /**
     * ElasticSearchConfig constructor.
     * @param configData
     */
    public ElasticSearchConfig(final ElasticConfigData configData) {
        this.elasticConfigData = configData;
    }

    /**
     * Get ElasticsearchClient.
     * @return ElasticsearchClient
     */
    @Bean
    public ElasticsearchClient elasticsearchClient() {
        URI uri = URI.create(this.elasticConfigData.getConnectionUrl());
        UriComponents serverUri = UriComponentsBuilder.fromUri(uri).build();

        this.restClient = RestClient.builder(
                new HttpHost(
                        Objects.requireNonNull(serverUri.getHost()),
                        serverUri.getPort(),
                        serverUri.getScheme()
                )
        ).setRequestConfigCallback(
                requestConfigBuilder -> requestConfigBuilder
                        .setConnectTimeout(this.elasticConfigData.getConnectionTimeout())
                        .setSocketTimeout(this.elasticConfigData.getSocketTimeout())
        ).build();

        RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

        return new ElasticsearchClient(transport);
    }

    /**
     * Close client connection.
     */
    @PreDestroy
    public void close() throws IOException {
        if (this.restClient != null) {
            this.restClient.close();
        }
    }
}
