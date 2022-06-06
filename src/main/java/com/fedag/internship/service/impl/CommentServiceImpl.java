package com.fedag.internship.service.impl;

import com.fedag.internship.domain.entity.CommentEntity;
import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.domain.exception.EntityNotFoundException;
import com.fedag.internship.domain.mapper.CommentMapper;
import com.fedag.internship.repository.CommentRepository;
import com.fedag.internship.service.CommentService;
import com.fedag.internship.service.UserService;
import lombok.RequiredArgsConstructor;
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
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserService userService;

    @Override
    public CommentEntity getCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment", "id", id));
    }

    @Override
    public Page<CommentEntity> getAllComments(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public CommentEntity createComment(Long userId, CommentEntity commentEntity) {
        final UserEntity userEntity = userService.getUserById(userId);
        userEntity.addComments(commentEntity);
        return commentRepository.save(commentEntity);
    }

    @Override
    @Transactional
    public CommentEntity updateComment(Long id, CommentEntity commentEntity) {
        CommentEntity target = this.getCommentById(id);
        CommentEntity result = commentMapper.merge(commentEntity, target);
        return commentRepository.save(result);
    }

    @Override
    @Transactional
    public void deleteComment(Long id) {
        CommentEntity comment = this.getCommentById(id);
        final UserEntity userEntity = userService.getUserById(comment.getUser().getId());
        userEntity.removeComments(comment);
        commentRepository.deleteById(id);
    }
}
