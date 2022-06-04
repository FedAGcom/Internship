package com.fedag.internship.controller;

import com.fedag.internship.domain.dto.CommentCreateDto;
import com.fedag.internship.domain.dto.CommentDto;
import com.fedag.internship.domain.dto.CommentUpdateDto;
import com.fedag.internship.domain.entity.CommentEntity;
import com.fedag.internship.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * Rest Controller for class {@link CommentEntity}.
 *
 * @author damir.iusupov
 * @since 2022-06-01
 */

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    /**
     * Method for finding Comment with id from DataBase and mapping
     * to CommentDto.
     *
     * @param id - id of Comment object
     * @return CommentDto with id
     */
    @GetMapping("{id}")
    public ResponseEntity<CommentDto> findById(@PathVariable Long id) {
        CommentDto result = commentService.findById(id);
        return new ResponseEntity<>(result, OK);
    }

    /**
     * Method for saving created Comment in DataBase.
     *
     * @param commentCreateDto - Comment object for saving
     * @return created CommentDto
     */
    @PostMapping
    public ResponseEntity<CommentDto> create(@RequestBody @Valid CommentCreateDto commentCreateDto) {
        CommentDto result = commentService.create(commentCreateDto);
        return new ResponseEntity<>(result, CREATED);
    }

    /**
     * Method for merge-updating Comment in DataBase.
     *
     * @param id               - id of Comment object
     * @param commentUpdateDto - CommentUpdateDto object for update
     * @return updated CommentDto
     */
    @PatchMapping("/{id}")
    public ResponseEntity<CommentDto> update(@PathVariable Long id,
                                             @RequestBody @Valid CommentUpdateDto commentUpdateDto) {
        CommentDto result = commentService.update(id, commentUpdateDto);
        return new ResponseEntity<>(result, CREATED);
    }

    /**
     * Method for deleting Comment.
     *
     * @param id - id of Comment object
     */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        commentService.deleteById(id);
        return new ResponseEntity(OK);
    }

    /**
     * Method for displaying the Page of Comments.
     * Default link: "comments".
     *
     * @return Page of CommentsDtos
     */
    @GetMapping
    public ResponseEntity<Page<CommentDto>> findAll(@PageableDefault(size = 5) Pageable pageable) {
        Page<CommentDto> result = commentService.findAll(pageable);
        return new ResponseEntity<>(result, OK);
    }
}
