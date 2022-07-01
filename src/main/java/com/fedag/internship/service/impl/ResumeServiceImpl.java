package com.fedag.internship.service.impl;

import com.fedag.internship.domain.entity.ResumeEntity;
import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.domain.exception.EntityNotFoundException;
import com.fedag.internship.domain.mapper.ResumeMapper;
import com.fedag.internship.repository.ResumeRepository;
import com.fedag.internship.service.CurrentUserService;
import com.fedag.internship.service.ResumeService;
import com.fedag.internship.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeRepository resumeRepository;
    private final UserService userService;
    private final ResumeMapper resumeMapper;
    private final CurrentUserService currentUserService;

    @Override
    @Transactional
    public ResumeEntity findById(Long id) {
        log.info("Получение резюме c Id: {}", id);
        ResumeEntity result = resumeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Резюме с Id: {} не найдено", id);
                    throw new EntityNotFoundException("Resume", "id", id);
                });
        log.info("Резюме c Id: {} получено", id);
        return result;
    }

    @Override
    @Transactional
    public Page<ResumeEntity> findAll(Pageable pageable) {
        log.info("Получение страницы с резюме");
        Page<ResumeEntity> result = resumeRepository.findAll(pageable);
        log.info("Страница с резюме получена");
        return result;
    }

    @Override
    @Transactional
    public ResumeEntity create(ResumeEntity resumeEntity) {
        log.info("Создание резюме");
        UserEntity userEntity = currentUserService.getCurrentUser();
        userEntity.setResume(resumeEntity);
        resumeEntity.setUser(userEntity);
        ResumeEntity result = resumeRepository.save(resumeEntity);
        log.info("Резюме для пользователя с Id: {} создано", userEntity.getId());
        return result;
    }

    @Override
    @Transactional
    public ResumeEntity update(Long id, ResumeEntity resumeEntity) {
        log.info("Обновление резюме с Id: {}", id);
        ResumeEntity target = this.findById(id);
        ResumeEntity update = resumeMapper.merge(resumeEntity, target);
        ResumeEntity result = resumeRepository.save(update);
        log.info("Резюме с Id: {} обновлено", id);
        return result;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("Удаление резюме с Id: {}", id);
        this.findById(id);
        resumeRepository.deleteById(id);
        log.info("Резюме с Id: {} удалена", id);
    }
}
