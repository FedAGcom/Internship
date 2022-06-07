package com.fedag.internship.domain.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedag.internship.domain.dto.request.CommentRequest;
import com.fedag.internship.domain.dto.request.CommentRequestUpdate;
import com.fedag.internship.domain.dto.response.CommentResponse;
import com.fedag.internship.domain.entity.CommentEntity;
import com.fedag.internship.domain.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * class CommentMapperImpl is implementation of {@link CommentMapper} interface.
 *
 * @author damir.iusupov
 * @since 2022-06-01
 */
@Component
@RequiredArgsConstructor
public class CommentMapperImpl implements CommentMapper {
    private final ObjectMapper objectMapper;

    @Override
    public CommentResponse toResponse(CommentEntity commentEntity) {
        return new CommentResponse()
                .setId(commentEntity.getId())
                .setText(commentEntity.getText())
                .setCreated(commentEntity.getCreated())
                .setUpdated(commentEntity.getUpdated())
                .setUserId(commentEntity.getUser().getId());
    }

    @Override
    public CommentEntity fromRequest(CommentRequest source) {
        return objectMapper.convertValue(source, CommentEntity.class);
    }

    @Override
    public CommentEntity fromRequestUpdate(CommentRequestUpdate source) {
        return objectMapper.convertValue(source, CommentEntity.class);
    }

    @Override
    public CommentEntity merge(CommentEntity source, CommentEntity target) {
        target.setText(source.getText());
        return target;
    }
}
