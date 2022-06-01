package com.fedag.internship.domain.mapper;

import com.fedag.internship.domain.dto.CommentCreateDto;
import com.fedag.internship.domain.dto.CommentDto;
import com.fedag.internship.domain.dto.CommentUpdateDto;
import com.fedag.internship.domain.entity.Comment;

/**
 * interface CommentMapper for Dto layer and for class {@link Comment}.
 *
 * @author damir.iusupov
 * @since 2022-06-01
 */
public interface CommentMapper {
    /**
     * Method for mapping Comment to CommentDto
     *
     * @param source - Comment object for mapping
     * @return CommentDto object
     */
    CommentDto toDto(Comment source);

    /**
     * Method for mapping CommentCreateDto to Comment
     *
     * @param source - CommentCreateDto object for mapping
     * @return Comment object
     */
    Comment fromCreateDto(CommentCreateDto source);

    /**
     * Method for mapping CommentUpdateDto to Comment
     *
     * @param source - CommentUpdateDto object for mapping
     * @return Comment object
     */
    Comment fromUpdateDto(CommentUpdateDto source);

    /**
     * Method for merge-update from source Comment object to target Comment object.
     *
     * @param source - Comment object source for update
     * @param target - Comment object target for update
     * @return merge-updated Comment object
     */
    Comment merge(Comment source, Comment target);
}
