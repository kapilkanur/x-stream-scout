package com.kk.elastic.index.client.service.impl;

import com.kk.configuration.ElasticConfigData;
import com.kk.elastic.index.client.service.ElasticIndexClient;
import com.kk.elastic.index.client.util.ElasticIndexUtil;
import com.kk.elastic.model.index.impl.TwitterIndexModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TwitterElasticIndexClient implements ElasticIndexClient<TwitterIndexModel> {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterElasticIndexClient.class);

    private final ElasticConfigData elasticConfigData;

    private final ElasticsearchOperations elasticsearchOperations;

    private final ElasticIndexUtil<TwitterIndexModel> elasticIndexUtil;

    /**
     * TwitterElasticIndexClient constructor.
     * @param configData
     * @param elasticOperations
     * @param indexUtil
     */
    public TwitterElasticIndexClient(final ElasticConfigData configData,
                                     final ElasticsearchOperations elasticOperations,
                                     final ElasticIndexUtil<TwitterIndexModel> indexUtil) {
        this.elasticConfigData = configData;
        this.elasticsearchOperations = elasticOperations;
        this.elasticIndexUtil = indexUtil;
    }

    /**
     * Save documents.
     * @param documents
     * @return List<String>
     */
    @Override
    public List<String> save(final List<TwitterIndexModel> documents) {
        List<IndexQuery> indexQueries = this.elasticIndexUtil.getIndexQueries(documents);

        this.elasticsearchOperations.bulkIndex(
                indexQueries,
                IndexCoordinates.of(this.elasticConfigData.getIndexName())
        );

        List<String> documentIds = indexQueries.stream()
                .map(IndexQuery::getId)
                .toList();

        LOG.info("Documents indexed successfully with type: {} and ids: {}", TwitterIndexModel.class.getName(), documentIds);
        return documentIds;
    }


}

