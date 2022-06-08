package com.fedag.internship.repository;

import com.fedag.internship.domain.entity.TraineePositionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionsSearcher {
    Page<TraineePositionEntity> search(String keyword, String searchCriteria, Pageable pageable);
}
