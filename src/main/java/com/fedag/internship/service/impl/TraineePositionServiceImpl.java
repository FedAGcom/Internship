package com.fedag.internship.service.impl;

import com.fedag.internship.domain.entity.TraineePositionEntity;
import com.fedag.internship.domain.exception.EntityNotFoundException;
import com.fedag.internship.domain.mapper.TraineePositionMapper;
import com.fedag.internship.repository.TraineePositionRepository;
import com.fedag.internship.service.TraineePositionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TraineePositionServiceImpl implements TraineePositionService {

    private final TraineePositionRepository positionRepository;

    private final TraineePositionMapper positionMapper;


    @Override
    public TraineePositionEntity getPositionById(Long id) {
        log.info("Получение позиции стажировки c Id: {}", id);
        TraineePositionEntity result = positionRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Позиция стажировки с Id: {} не найдена", id);
                    throw new EntityNotFoundException("TraineePosition", "Id", id);
                });
        log.info("Позиция стажировки c Id: {} получена", id);
        return result;
    }

    @Override
    public Page<TraineePositionEntity> getAllPositions(Pageable pageable) {
        log.info("Получение страницы с позициями стажировки");
        Page<TraineePositionEntity> result = positionRepository.findAll(pageable);
        log.info("Страница с позициями стажировки получена");
        return result;
    }

    @Override
    @Transactional
    public TraineePositionEntity createPosition(TraineePositionEntity positionEntity) {
        log.info("Создание позиции стажировки");
        TraineePositionEntity result = positionRepository.save(positionEntity);
        log.info("Позиция стажировки создана");
        return result;
    }

    @Override
    @Transactional
    public TraineePositionEntity updatePosition(Long id, TraineePositionEntity positionEntity) {
        log.info("Обновление позиции стажировки с Id: {}", id);
        TraineePositionEntity target = this.getPositionById(id);
        TraineePositionEntity update = positionMapper.merge(positionEntity, target);
        TraineePositionEntity result = positionRepository.save(update);
        log.info("Позиция стажировки с Id: {} обновлена", id);
        return result;
    }

    @Override
    @Transactional
    public void deletePosition(Long id) {
        log.info("Удаление позиции стажировки с Id: {}", id);
        this.getPositionById(id);
        positionRepository.deleteById(id);
        log.info("Позиция стажировки с Id: {} удалена", id);
    }

    @Override
    public Page<TraineePositionEntity> findPositionByCompany(String keyword, Pageable pageable) {
        return positionRepository.search(keyword, "company", pageable);
    }
}
