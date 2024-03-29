package com.fedag.internship.service.impl;

import com.fedag.internship.domain.document.CompanyElasticSearchEntity;
import com.fedag.internship.domain.entity.CompanyEntity;
import com.fedag.internship.repository.CompanyElasticSearchRepository;
import com.fedag.internship.service.CompanyElasticSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import static com.fedag.internship.domain.document.Indexes.COMPANY_INDEX;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyElasticSearchServiceImpl implements CompanyElasticSearchService {
    private final ElasticsearchOperations elasticsearchOperations;
    private final CompanyElasticSearchRepository companyElasticSearchRepository;

    @Override
    @Transactional
    public CompanyElasticSearchEntity save(CompanyEntity companyEntity) {
        log.info("Сохранение компании в Elasticsearch");
        CompanyElasticSearchEntity elsEntity = new CompanyElasticSearchEntity(companyEntity.getName(), companyEntity.getId());
        companyElasticSearchRepository.save(elsEntity);
        log.info("Компания в Elasticsearch сохранена");
        return elsEntity;
    }

    @Override
    @Transactional
    public Page<CompanyElasticSearchEntity> elasticsearchByName(Pageable pageable, String name) {
        log.info("Полнотекстовый поиск компаний по имени: {}", name);
        name = name.toLowerCase();
        QueryBuilder fuzzyQuery = QueryBuilders
                .matchQuery("name", name)
                .fuzziness(Fuzziness.AUTO);
        QueryBuilder wildcardQuery = QueryBuilders
                .wildcardQuery("name", "*" + name + "*");
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
        productHits.forEach(searchHit -> companies.add(searchHit.getContent()));
        companies.forEach(c -> System.out.println(c.getName()));
        PageImpl<CompanyElasticSearchEntity> result = new PageImpl<>(companies, pageable, companies.size());
        log.info("Список компаний по имени: {} получен", name);
        return result;
    }
}
