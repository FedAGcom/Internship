package com.fedag.internship.service.impl;

import com.fedag.internship.domain.entity.CommentEntity;
import com.fedag.internship.domain.entity.CompanyEntity;
import com.fedag.internship.domain.entity.TraineePositionEntity;
import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.domain.exception.EntityNotFoundException;
import com.fedag.internship.domain.mapper.CommentMapper;
import com.fedag.internship.repository.CommentRepository;
import com.fedag.internship.service.CommentService;
import com.fedag.internship.service.CompanyService;
import com.fedag.internship.service.CurrentUserService;
import com.fedag.internship.service.TraineePositionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * class CommentServiceImpl for create connections between CommentRepository and CommentController.
 * Implementation of {@link CommentService} interface.
 * Wrapper for {@link CommentRepository} and plus business logic.
 *
 * @author damir.iusupov
 * @since 2022-06-01
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CurrentUserService currentUserService;
    private final CommentMapper commentMapper;
    private final CompanyService companyService;
    private final TraineePositionService traineePositionService;

    @Override
    public CommentEntity findById(Long id) {
        log.info("Получение комментария c Id: {}", id);
        CommentEntity result = commentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Комментарий с Id: {} не найден", id);
                    throw new EntityNotFoundException("Comment", "id", id);
                });
        log.info("Комментарий c Id: {} получен", id);
        return result;
    }

    @Override
    public Page<CommentEntity> findAll(Pageable pageable) {
        log.info("Получение страницы с комментариями");
        Page<CommentEntity> result = commentRepository.findAll(pageable);
        log.info("Страница с комментариями получена");
        return result;
    }

    @Override
    @Transactional
    public CommentEntity createForCompany(Long companyId, CommentEntity commentEntity) {
        log.info("Создание комментария для компании с Id: {}", companyId);
        final UserEntity userEntity = currentUserService.getCurrentUser();
        userEntity.addComments(commentEntity);
        final CompanyEntity companyEntity = companyService.findById(companyId);
        companyEntity.addComments(commentEntity);
        CommentEntity result = commentRepository.save(commentEntity);
        log.info("Комментарий для компании с Id: {} создан", companyId);
        return result;
    }

    @Override
    @Transactional
    public CommentEntity createForTraineePosition(Long traineePositionId,
                                                  CommentEntity commentEntity) {
        log.info("Создание комментария для позиции стажировки с Id: {}", traineePositionId);
        final UserEntity userEntity = currentUserService.getCurrentUser();
        userEntity.addComments(commentEntity);
        final TraineePositionEntity traineePosition = traineePositionService.findById(traineePositionId);
        traineePosition.addComments(commentEntity);
        CommentEntity result = commentRepository.save(commentEntity);
        log.info("Комментарий для позиции стажировки с Id: {} создан", traineePositionId);
        return result;
    }

    @Override
    @Transactional
    public CommentEntity update(Long id, CommentEntity commentEntity) {
        log.info("Обновление комментария с Id: {}", id);
        CommentEntity target = this.findById(id);
        CommentEntity update = commentMapper.merge(commentEntity, target);
        CommentEntity result = commentRepository.save(update);
        log.info("Комментарий с Id: {} обновлен", id);
        return result;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("Удаление комментария с Id: {}", id);
        CommentEntity comment = this.findById(id);
        final UserEntity userEntity = comment.getUser();
        userEntity.removeComments(comment);
        if (comment.getCompany() != null) {
            final CompanyEntity companyEntity = comment.getCompany();
            companyEntity.removeComments(comment);
        }
        if (comment.getTraineePosition() != null) {
            final TraineePositionEntity traineePosition = comment.getTraineePosition();
            traineePosition.removeComments(comment);
        }
        commentRepository.deleteById(id);
        log.info("Комментарий с Id: {} удален", id);
    }
}
