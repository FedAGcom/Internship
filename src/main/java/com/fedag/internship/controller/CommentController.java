package com.fedag.internship.controller;

import com.fedag.internship.domain.dto.CommentRequest;
import com.fedag.internship.domain.dto.CommentRequestUpdate;
import com.fedag.internship.domain.dto.CommentResponse;
import com.fedag.internship.domain.dto.CompanyResponse;
import com.fedag.internship.domain.mapper.CommentMapper;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * Rest Controller for class {@link com.fedag.internship.domain.entity.CommentEntity}.
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

    @GetMapping("{id}")
    public ResponseEntity<CommentResponse> findById(@PathVariable Long id) {
        CommentResponse result = Optional.of(id)
                .map(commentService::getCommentById)
                .map(commentMapper::toResponse)
                .orElseThrow();
        return new ResponseEntity<>(result, OK);
    }

    @GetMapping
    public ResponseEntity<Page<CommentResponse>> findAll(@PageableDefault(size = 5) Pageable pageable) {
        Page<CommentResponse> result = commentService.getAllComments(pageable)
                .map(commentMapper::toResponse);
        return new ResponseEntity<>(result, OK);
    }

    @PostMapping
    public ResponseEntity<CommentResponse> create(@RequestParam Long userId,
                                                  @RequestBody @Valid CommentRequest commentRequest) {
        CommentResponse result = Optional.ofNullable(commentRequest)
                .map(commentMapper::fromRequest)
                .map(comment -> commentService.createComment(userId, comment))
                .map(commentMapper::toResponse)
                .orElseThrow();
        return new ResponseEntity<>(result, CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommentResponse> update(@PathVariable Long id,
                                                  @RequestBody @Valid CommentRequestUpdate commentRequestUpdate) {
        CommentResponse result = Optional.ofNullable(commentRequestUpdate)
                .map(commentMapper::fromRequestUpdate)
                .map(comment -> commentService.updateComment(id, comment))
                .map(commentMapper::toResponse)
                .orElseThrow();
        return new ResponseEntity<>(result, OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(OK);
    }


}
