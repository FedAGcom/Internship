package com.fedag.internship.service.impl;

import com.fedag.internship.domain.entity.CompanyEntity;
import com.fedag.internship.domain.entity.TraineePositionEntity;
import com.fedag.internship.domain.exception.EntityNotFoundException;
import com.fedag.internship.domain.mapper.TraineePositionMapper;
import com.fedag.internship.repository.TraineePositionRepository;
import com.fedag.internship.service.CompanyService;
import com.fedag.internship.service.PositionElasticSearchService;
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
    private final PositionElasticSearchService positionElasticSearchService;
    private final TraineePositionRepository positionRepository;

    private final CompanyService companyService;
    private final TraineePositionMapper positionMapper;

    @Override
    public TraineePositionEntity findById(Long id) {
        log.info("Получение позиции стажировки c Id: {}", id);
        TraineePositionEntity result = positionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Позиция стажировки с Id: {} не найдена", id);
                    throw new EntityNotFoundException("TraineePosition", "Id", id);
                });
        log.info("Позиция стажировки c Id: {} получена", id);
        return result;
    }

    @Override
    public Page<TraineePositionEntity> findAll(Pageable pageable) {
        log.info("Получение страницы с позициями стажировки");
        Page<TraineePositionEntity> result = positionRepository.findAll(pageable);
        log.info("Страница с позициями стажировки получена");
        return result;
    }

    @Override
    @Transactional
    public TraineePositionEntity create(Long companyId, TraineePositionEntity positionEntity) {
        log.info("Создание позиции стажировки");
        final CompanyEntity companyEntity = companyService.findById(companyId);
        companyEntity.addPosition(positionEntity);
        TraineePositionEntity result = positionRepository.save(positionEntity);
        positionElasticSearchService.save(positionEntity);
        log.info("Позиция стажировки создана для компании с Id {}", companyId);
        return result;
    }

    @Override
    @Transactional
    public TraineePositionEntity update(Long id, TraineePositionEntity positionEntity) {
        log.info("Обновление позиции стажировки с Id: {}", id);
        TraineePositionEntity target = this.findById(id);
        TraineePositionEntity update = positionMapper.merge(positionEntity, target);
        TraineePositionEntity result = positionRepository.save(update);
        log.info("Позиция стажировки с Id: {} обновлена", id);
        return result;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("Удаление позиции стажировки с Id: {}", id);
        this.findById(id);
        positionRepository.deleteById(id);
        log.info("Позиция стажировки с Id: {} удалена", id);
    }

    @Override
    @Transactional
    public Page<TraineePositionEntity> searchByCompany(String keyword, Pageable pageable) {
        log.info("Получение страницы с позициями по компании");
        Page<TraineePositionEntity> result = positionElasticSearchService
                .elasticsearchByCompany(pageable, keyword)
                .map(el -> this.findById(el.getCompanyEntityId()));
        log.info("Страница с позициями по компании получена");
        return result;
    }
}
