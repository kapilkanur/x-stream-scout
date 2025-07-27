package com.kk.elastic.index.client.service;

import com.kk.elastic.model.index.IndexModel;

import java.util.List;

public interface ElasticIndexClient<T extends IndexModel> {
    /**
     * Save documents.
     * @param documents
     * @return List<String>
     */
    List<String> save(List<T> documents);
}
