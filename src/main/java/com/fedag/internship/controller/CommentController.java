package com.fedag.internship.controller;

import com.fedag.internship.domain.dto.CommentCreateDto;
import com.fedag.internship.domain.dto.CommentDto;
import com.fedag.internship.domain.dto.CommentUpdateDto;
import com.fedag.internship.domain.entity.Comment;
import com.fedag.internship.domain.mapper.CommentMapper;
import com.fedag.internship.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * Rest Controller for class {@link Comment}.
 *
 * @author damir.iusupov
 * @since 2022-06-01
 */

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    /**
     * Method for finding Comment with id from DataBase and mapping
     * to CommentDto.
     *
     * @param id - id of Comment object
     * @return CommentDto with id
     */
    @GetMapping("{id}")
    public ResponseEntity<CommentDto> findById(@PathVariable Long id) {
        CommentDto result = Optional.of(id)
                .map(commentService::findById)
                .map(commentMapper::toDto)
                .orElseThrow();
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
        CommentDto result = Optional.ofNullable(commentCreateDto)
                .map(commentMapper::fromCreateDto)
                .map(commentService::create)
                .map(commentMapper::toDto)
                .orElseThrow();
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
        CommentDto result = Optional.ofNullable(commentUpdateDto)
                .map(commentMapper::fromUpdateDto)
                .map(comment -> commentService.update(id, comment))
                .map(commentMapper::toDto)
                .orElseThrow();
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
    public ResponseEntity<Page<CommentDto>> findAll() {
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<CommentDto> result = commentService
                .findAll(pageRequest)
                .map(commentMapper::toDto);
        return new ResponseEntity<>(result, OK);
    }
}
