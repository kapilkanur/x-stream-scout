package com.kk.kafka.to.elastic.service.transformer;

import com.kk.elastic.model.index.impl.TwitterIndexModel;
import com.kk.avro.TweetAvroModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AvroToElasticModelTransformer {

    /**
     * Get twitter index models.
     * @param avroModels
     * @return
     */
    public List<TwitterIndexModel> getElasticModels(final List<TweetAvroModel> avroModels) {
        return avroModels.stream()
                .map(avroModel -> TwitterIndexModel
                        .builder()
                        .id(avroModel.getId())
                        .id(String.valueOf(avroModel.getId()))
                        .message(avroModel.getMessage())
                        .timestamp(avroModel.getTimestamp())
                        .build()
                ).collect(Collectors.toList());
    }
}
