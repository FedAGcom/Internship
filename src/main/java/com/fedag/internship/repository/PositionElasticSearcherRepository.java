package com.fedag.internship.repository;

import com.fedag.internship.domain.document.PositionElasticSearchEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PositionElasticSearcherRepository extends ElasticsearchRepository<PositionElasticSearchEntity, String> {
}
