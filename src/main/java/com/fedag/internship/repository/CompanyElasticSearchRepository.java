package com.fedag.internship.repository;

import com.fedag.internship.domain.elasticsearch.CompanyElasticSearchEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyElasticSearchRepository extends ElasticsearchRepository<CompanyElasticSearchEntity, String> {
}
