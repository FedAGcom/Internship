package com.fedag.internship.domain.mapper;

import com.fedag.internship.domain.dto.request.CommentRequest;
import com.fedag.internship.domain.dto.request.CommentRequestUpdate;
import com.fedag.internship.domain.dto.response.CommentResponse;
import com.fedag.internship.domain.entity.CommentEntity;

/**
 * interface CommentMapper for Dto layer and for class {@link CommentEntity}.
 *
 * @author damir.iusupov
 * @since 2022-06-01
 */
public interface CommentMapper {
    CommentResponse toResponse(CommentEntity source);

    CommentEntity fromRequest(CommentRequest source);

    CommentEntity fromRequestUpdate(CommentRequestUpdate source);

    CommentEntity merge(CommentEntity source, CommentEntity target);
}
