package com.fedag.internship.service;

import com.fedag.internship.domain.document.PositionElasticSearchEntity;
import com.fedag.internship.domain.entity.TraineePositionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PositionElasticSearchService {
    PositionElasticSearchEntity save(TraineePositionEntity positionEntity);

    Page<PositionElasticSearchEntity> elasticsearchByName(Pageable pageable, String name);
}
