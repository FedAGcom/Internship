package com.fedag.internship.service;

import com.fedag.internship.domain.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * interface CommentService for class {@link Comment}.
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
    Comment findById(Long id);

    /**
     * Method for save created Comment in DataBase.
     *
     * @param comment - Comment object for saving
     * @return created Comment
     */
    Comment create(Comment comment);

    /**
     * Method for replacing fields of the old Comment with a new one and
     * save updated Comment in DataBase.
     *
     * @param id     - id of Comment object
     * @param source - Comment object for replace
     * @return updated Comment
     */
    Comment update(Long id, Comment source);

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
    Page<Comment> findAll(Pageable pageable);
}
