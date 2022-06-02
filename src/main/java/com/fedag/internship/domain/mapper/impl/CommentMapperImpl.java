package com.fedag.internship.domain.mapper.impl;

import com.fedag.internship.domain.dto.CommentCreateDto;
import com.fedag.internship.domain.dto.CommentDto;
import com.fedag.internship.domain.dto.CommentUpdateDto;
import com.fedag.internship.domain.entity.Comment;
import com.fedag.internship.domain.mapper.CommentMapper;
import org.springframework.stereotype.Component;

/**
 * class CommentMapperImpl
 *
 * @author damir.iusupov
 * @since 2022-06-01
 */
@Component
public class CommentMapperImpl implements CommentMapper {
    @Override
    public CommentDto toDto(Comment source) {
        return new CommentDto()
                .setId(source.getId())
                .setText(source.getText())
                .setCreated(source.getCreated())
                .setUpdated(source.getUpdated());
    }

    @Override
    public Comment fromCreateDto(CommentCreateDto source) {
        Comment result = new Comment();
        result.setText(source.getText());
        return result;
    }

    @Override
    public Comment fromUpdateDto(CommentUpdateDto source) {
        Comment result = new Comment();
        result.setText(source.getText());
        return result;
    }

    @Override
    public Comment merge(Comment source, Comment target) {
        target.setText(source.getText());
        return target;
    }
}
