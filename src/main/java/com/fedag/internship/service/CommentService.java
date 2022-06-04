package com.fedag.internship.service;

import com.fedag.internship.domain.dto.CommentCreateDto;
import com.fedag.internship.domain.dto.CommentDto;
import com.fedag.internship.domain.dto.CommentUpdateDto;
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
    /**
     * Method for displaying Comment with id from DataBase.
     *
     * @param id - id of Comment object
     * @return Comment with id
     */
    CommentDto findById(Long id);

    /**
     * Method for save created Comment in DataBase.
     *
     * @param commentCreateDto - Comment's Dto object for saving
     * @return created Comment
     */
    CommentDto create(CommentCreateDto commentCreateDto);

    /**
     * Method for replacing fields of the old Comment with a new one and
     * save updated Comment in DataBase.
     *
     * @param id               - id of Comment object
     * @param commentUpdateDto - Comment's Dto object for updating
     * @return updated Comment
     */
    CommentDto update(Long id, CommentUpdateDto commentUpdateDto);

    /**
     * Method for delete Comment.
     *
     * @param id - id of Comment object
     */
    void deleteById(Long id);

    /**
     * The method for displaying Comments as a Page with the possibility to resize
     * and sort this Page
     *
     * @param pageable - param for pagination information
     * @return Page of Comments
     */
    Page<CommentDto> findAll(Pageable pageable);
}
