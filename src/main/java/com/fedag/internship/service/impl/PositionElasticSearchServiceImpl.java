package com.fedag.internship.service.impl;

import com.fedag.internship.domain.document.Indexes;
import com.fedag.internship.domain.document.PositionElasticSearchEntity;
import com.fedag.internship.domain.entity.TraineePositionEntity;
import com.fedag.internship.repository.PositionElasticSearcherRepository;
import com.fedag.internship.service.PositionElasticSearchService;
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

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.fedag.internship.domain.document.Indexes.POSITION_INDEX;

@RequiredArgsConstructor
@Service
public class PositionElasticSearchServiceImpl implements PositionElasticSearchService {
    private final ElasticsearchOperations elasticsearchOperations;
    private final PositionElasticSearcherRepository positionElasticSearcherRepository;

    @Override
    @Transactional
    public PositionElasticSearchEntity savePosition(TraineePositionEntity positionEntity) {
        PositionElasticSearchEntity posEntity = new PositionElasticSearchEntity(positionEntity.getName(), positionEntity.getId());
        positionElasticSearcherRepository.save(posEntity);
        return posEntity;
    }

    @Override
    @Transactional
    public Page<PositionElasticSearchEntity> searchByCompany(Pageable pageable, String name) {
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
        SearchHits<PositionElasticSearchEntity> productHits = elasticsearchOperations
                .search(query, PositionElasticSearchEntity.class, IndexCoordinates.of(POSITION_INDEX));
        List<PositionElasticSearchEntity> positions = new ArrayList<>();
        productHits.forEach(searchHit -> positions.add(searchHit.getContent()));
        return new PageImpl<>(positions, pageable, positions.size());
    }
}

