package com.fedag.internship.repository.impl;

import com.fedag.internship.domain.entity.CompanyEntity;
import com.fedag.internship.repository.CompanySearcher;
import lombok.RequiredArgsConstructor;
import org.apache.lucene.search.Query;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Transactional
public class CompanySearcherImpl implements CompanySearcher {
    private final EntityManager entityManager;

    @Override
    public Page<CompanyEntity> search(String keyword, String searchCriteria, Pageable pageable) {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager.unwrap(SessionImplementor.class));
        String lowerCaseKeyword = keyword.toLowerCase();
        QueryBuilder queryBuilder = fullTextEntityManager
                .getSearchFactory()
                .buildQueryBuilder()
                .forEntity(CompanyEntity.class)
                .get();
        Query fullMatchQuery = queryBuilder
                .keyword()
                .wildcard()
                .onField(searchCriteria)
                .matching("*" + lowerCaseKeyword + "*")
                .createQuery();
        Query fuzzyQuery = queryBuilder
                .keyword()
                .fuzzy()
                .withEditDistanceUpTo(1)
                .onField(searchCriteria)
                .matching(lowerCaseKeyword)
                .createQuery();
        Query allCompaniesQuery = queryBuilder
                .all()
                .createQuery();
        Query resultQuery = queryBuilder
                .bool()
                .should(fullMatchQuery)
                .should(fuzzyQuery)
                .should(allCompaniesQuery)
                .createQuery();
        FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(resultQuery, CompanyEntity.class);
        fullTextQuery.setFirstResult(pageable.getPageSize() * pageable.getPageNumber());
        fullTextQuery.setMaxResults(pageable.getPageSize());
        List<CompanyEntity> companies = fullTextQuery.getResultList();
        return new PageImpl<>(companies, pageable, companies.size());
    }
}
