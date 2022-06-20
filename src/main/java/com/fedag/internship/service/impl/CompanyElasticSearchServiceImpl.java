package com.fedag.internship.service.impl;

import com.fedag.internship.domain.elasticsearch.CompanyElasticSearchEntity;
import com.fedag.internship.domain.entity.CompanyEntity;
import com.fedag.internship.repository.CompanyElasticSearchRepository;
import com.fedag.internship.service.CompanyElasticSearchService;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CompanyElasticSearchServiceImpl implements CompanyElasticSearchService {
    private static final String COMPANY_INDEX = "companies";
    private final ElasticsearchOperations elasticsearchOperations;
    private final CompanyElasticSearchRepository companyElasticSearchRepository;

    @Transactional
    @Override
    public CompanyElasticSearchEntity saveCompany(CompanyEntity companyEntity) {
        CompanyElasticSearchEntity elsEntity = new CompanyElasticSearchEntity(companyEntity.getName(), companyEntity.getId());
        companyElasticSearchRepository.save(elsEntity);
        return elsEntity;
    }

    @Transactional
    @Override
    public Page<CompanyElasticSearchEntity> searchByName(Pageable pageable, String name) {
        QueryBuilder fuzzyQuery = QueryBuilders
                .matchQuery("name", name)
                .fuzziness(Fuzziness.AUTO);
        QueryBuilder wildcardQuery = QueryBuilders
                .wildcardQuery("name",  "*"+ name + "*");
        QueryBuilder allQuery = QueryBuilders
                .matchAllQuery();
        QueryBuilder searchQuery = QueryBuilders
                .boolQuery()
                .should(fuzzyQuery)
                .should(wildcardQuery)
                .should(allQuery);
        Query query = new NativeSearchQueryBuilder()
                .withQuery(searchQuery)
                .withPageable(pageable)
                .build();
        SearchHits<CompanyElasticSearchEntity> productHits = elasticsearchOperations
                .search(query, CompanyElasticSearchEntity.class, IndexCoordinates.of(COMPANY_INDEX));
        List<CompanyElasticSearchEntity> companies = new ArrayList<>();
        productHits.forEach(searchHit-> {companies.add(searchHit.getContent());
            System.out.println(searchHit.getContent().getName());
        });
        return new PageImpl<>(companies, pageable, companies.size());
    }
}
