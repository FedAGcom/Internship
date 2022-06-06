package com.fedag.internship.service;

import com.fedag.internship.domain.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * interface CommentService for class {@link com.fedag.internship.domain.entity.CommentEntity}.
 *
 * @author damir.iusupov
 * @since 2022-06-01
 */
public interface CommentService {
    CommentEntity getCommentById(Long id);

    Page<CommentEntity> getAllComments(Pageable pageable);

    CommentEntity createComment(Long userId, CommentEntity commentEntity);

    CommentEntity updateComment(Long id, CommentEntity commentEntity);

    void deleteComment(Long id);
}
