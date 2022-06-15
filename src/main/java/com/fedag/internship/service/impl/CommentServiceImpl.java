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
import com.fedag.internship.service.TraineePositionService;
import com.fedag.internship.service.UserService;
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
    private final CommentMapper commentMapper;
    private final UserService userService;
    private final CompanyService companyService;
    private final TraineePositionService traineePositionService;

    @Override
    public CommentEntity getCommentById(Long id) {
        log.info("Получение комментария c Id: {}", id);
        CommentEntity result = commentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Комментарий с Id: {} не найден", id);
                    throw new EntityNotFoundException("Comment", "id", id);
                });
        log.info("Комментарий c Id: {} получен", id);
        return result;
    }

    @Override
    public Page<CommentEntity> getAllComments(Pageable pageable) {
        log.info("Получение страницы с комментариями");
        Page<CommentEntity> result = commentRepository.findAll(pageable);
        log.info("Страница с комментариями получена");
        return result;
    }

    @Override
    @Transactional
    public CommentEntity createCommentForCompany(Long userId, Long companyId, CommentEntity commentEntity) {
        log.info("Создание комментария для компании с Id {} от пользователя с Id: {}", companyId, userId);
        final UserEntity userEntity = userService.getUserById(userId);
        userEntity.addComments(commentEntity);
        final CompanyEntity companyEntity = companyService.getCompanyById(companyId);
        companyEntity.addComments(commentEntity);
        CommentEntity result = commentRepository.save(commentEntity);
        log.info("Комментарий для компании с Id {} от пользователя с Id: {} создан", companyId, userId);
        return result;
    }

    @Override
    @Transactional
    public CommentEntity createCommentForTraineePosition(Long userId,
                                                         Long traineePositionId,
                                                         CommentEntity commentEntity) {
        log.info("Создание комментария для позиции стажировки с Id {} от пользователя с Id: {}", traineePositionId, userId);
        final UserEntity userEntity = userService.getUserById(userId);
        userEntity.addComments(commentEntity);
        final TraineePositionEntity traineePosition = traineePositionService.getPositionById(traineePositionId);
        traineePosition.addComments(commentEntity);
        CommentEntity result = commentRepository.save(commentEntity);
        log.info("Комментарий для позиции стажировки с Id {} от пользователя с Id: {} создан", traineePositionId, userId);
        return result;
    }

    @Override
    @Transactional
    public CommentEntity updateComment(Long id, CommentEntity commentEntity) {
        log.info("Обновление комментария с Id: {}", id);
        CommentEntity target = this.getCommentById(id);
        CommentEntity update = commentMapper.merge(commentEntity, target);
        CommentEntity result = commentRepository.save(update);
        log.info("Комментарий с Id: {} обновлен", id);
        return result;
    }

    @Override
    @Transactional
    public void deleteComment(Long id) {
        log.info("Удаление комментария с Id: {}", id);
        CommentEntity comment = this.getCommentById(id);
        final UserEntity userEntity = userService.getUserById(comment.getUser().getId());
        userEntity.removeComments(comment);
        commentRepository.deleteById(id);
        log.info("Комментарий с Id: {} удален", id);
    }
}
