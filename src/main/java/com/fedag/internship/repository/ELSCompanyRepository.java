package com.fedag.internship.repository;

import com.fedag.internship.domain.entity.CompanyELSEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ELSCompanyRepository extends ElasticsearchRepository<CompanyELSEntity, String> {
}
