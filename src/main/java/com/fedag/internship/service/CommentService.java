package com.fedag.internship.service;

import com.fedag.internship.domain.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * interface CommentService for class {@link CommentEntity}.
 *
 * @author damir.iusupov
 * @since 2022-06-01
 */
public interface CommentService {
    CommentEntity findById(Long id);

    Page<CommentEntity> findAll(Pageable pageable);

    CommentEntity createForCompany(Long companyId, CommentEntity commentEntity);

    CommentEntity createForTraineePosition(Long traineePositionId,
                                           CommentEntity commentEntity);

    CommentEntity update(Long id, CommentEntity commentEntity);

    void deleteById(Long id);
}
