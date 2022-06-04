package com.fedag.internship.domain.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedag.internship.domain.dto.CommentCreateDto;
import com.fedag.internship.domain.dto.CommentDto;
import com.fedag.internship.domain.dto.CommentUpdateDto;
import com.fedag.internship.domain.entity.CommentEntity;
import com.fedag.internship.domain.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * class CommentMapperImpl
 *
 * @author damir.iusupov
 * @since 2022-06-01
 */
@Component
@RequiredArgsConstructor
public class CommentMapperImpl implements CommentMapper {
    private final ObjectMapper objectMapper;

    @Override
    public CommentDto toDto(CommentEntity source) {
        return objectMapper.convertValue(source, CommentDto.class);
    }

    @Override
    public CommentEntity fromCreateDto(CommentCreateDto source) {
        return objectMapper.convertValue(source, CommentEntity.class);
    }

    @Override
    public CommentEntity fromUpdateDto(CommentUpdateDto source) {
        return objectMapper.convertValue(source, CommentEntity.class);
    }

    @Override
    public CommentEntity merge(CommentEntity source, CommentEntity target) {
        target.setText(source.getText());
        return target;
    }
}
