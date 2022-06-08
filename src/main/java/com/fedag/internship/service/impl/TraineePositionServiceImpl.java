package com.fedag.internship.service.impl;

import com.fedag.internship.domain.entity.CompanyEntity;
import com.fedag.internship.domain.entity.TraineePositionEntity;
import com.fedag.internship.domain.exception.EntityNotFoundException;
import com.fedag.internship.domain.mapper.TraineePositionMapper;
import com.fedag.internship.repository.TraineePositionRepository;
import com.fedag.internship.service.TraineePositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TraineePositionServiceImpl implements TraineePositionService {
    private final TraineePositionRepository positionRepository;
    private final TraineePositionMapper positionMapper;

    @Override
    public TraineePositionEntity getPositionById(Long id) {
        return positionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TraineePosition", "Id", id));
    }

    @Override
    public Page<TraineePositionEntity> getAllPositions(Pageable pageable) {
        return positionRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public TraineePositionEntity createPosition(TraineePositionEntity positionEntity) {
        return positionRepository.save(positionEntity);
    }

    @Override
    @Transactional
    public TraineePositionEntity updatePosition(Long id, TraineePositionEntity positionEntity) {
        TraineePositionEntity target = this.getPositionById(id);
        TraineePositionEntity result = positionMapper.merge(positionEntity, target);
        return positionRepository.save(result);
    }

    @Override
    @Transactional
    public void deletePosition(Long id) {
        this.getPositionById(id);
        positionRepository.deleteById(id);
    }

    @Override
    public Page<TraineePositionEntity> findPositionByCompanyName(String keyword, Pageable pageable) {
        return positionRepository.search(keyword, "name", pageable);
    }
}
