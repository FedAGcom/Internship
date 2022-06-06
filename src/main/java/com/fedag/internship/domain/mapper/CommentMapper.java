package com.fedag.internship.domain.mapper;

import com.fedag.internship.domain.dto.CommentRequest;
import com.fedag.internship.domain.dto.CommentResponse;
import com.fedag.internship.domain.dto.CommentRequestUpdate;

/**
 * interface CommentMapper for Dto layer and for class {@link com.fedag.internship.domain.entity.CommentEntity}.
 *
 * @author damir.iusupov
 * @since 2022-06-01
 */
public interface CommentMapper {
    CommentResponse toResponse(com.fedag.internship.domain.entity.CommentEntity source);

    com.fedag.internship.domain.entity.CommentEntity fromRequest(CommentRequest source);

    com.fedag.internship.domain.entity.CommentEntity fromRequestUpdate(CommentRequestUpdate source);

    com.fedag.internship.domain.entity.CommentEntity merge(com.fedag.internship.domain.entity.CommentEntity source, com.fedag.internship.domain.entity.CommentEntity target);
}
