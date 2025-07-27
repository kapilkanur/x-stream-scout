package com.kk.elastic.model.index.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kk.elastic.model.index.IndexModel;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Data
@Builder
@Document(indexName = "#{elasticConfigData.indexName}")
public class TwitterIndexModel implements IndexModel {

    @JsonProperty
    private String id;

    @JsonProperty
    private String username;

    @JsonProperty
    private String message;

    @JsonProperty
    @Field(type = FieldType.Long)
    private long timestamp;

    @JsonProperty
    @Field(type = FieldType.Keyword)
    private List<String> hashtags;
}
