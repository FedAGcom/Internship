package com.fedag.internship.service.impl;

import com.fedag.internship.domain.entity.Comment;
import com.fedag.internship.domain.mapper.CommentMapper;
import com.fedag.internship.exception.EntityNotFoundException;
import com.fedag.internship.repository.CommentRepository;
import com.fedag.internship.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * class CommentServiceImpl for create connections between CommentRepository and CommentController.
 * Implementation of {@link CommentService} interface.
 * Wrapper for {@link CommentRepository} and plus business logic.
 *
 * @author damir.iusupov
 * @since 2022-06-01
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment", "id", id));
    }

    @Override
    @Transactional
    public Comment create(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public Comment update(Long id, Comment source) {
        return Optional.of(id)
                .map(this::findById)
                .map(target -> commentMapper.merge(source, target))
                .orElseThrow();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        this.findById(id);
        commentRepository.deleteById(id);
    }

    @Override
    public Page<Comment> findAll(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }
}
