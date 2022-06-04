package com.fedag.internship.service.impl;

import com.fedag.internship.domain.dto.CommentCreateDto;
import com.fedag.internship.domain.dto.CommentDto;
import com.fedag.internship.domain.dto.CommentUpdateDto;
import com.fedag.internship.domain.entity.CommentEntity;
import com.fedag.internship.domain.exception.EntityNotFoundException;
import com.fedag.internship.domain.mapper.CommentMapper;
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
    public CommentDto findById(Long id) {
        return commentRepository.findById(id)
                .map(commentMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Comment", "id", id));
    }

    @Override
    @Transactional
    public CommentDto create(CommentCreateDto commentCreateDto) {
        return Optional.ofNullable(commentCreateDto)
                .map(commentMapper::fromCreateDto)
                .map(commentRepository::save)
                .map(commentMapper::toDto)
                .orElseThrow();
    }

    @Override
    @Transactional
    public CommentDto update(Long id, CommentUpdateDto commentUpdateDto) {
        CommentEntity target = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment", "id", id));
        return Optional.ofNullable(commentUpdateDto)
                .map(commentMapper::fromUpdateDto)
                .map(source -> commentMapper.merge(source, target))
                .map(commentMapper::toDto)
                .orElseThrow();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        this.findById(id);
        commentRepository.deleteById(id);
    }

    @Override
    public Page<CommentDto> findAll(Pageable pageable) {
        return commentRepository.findAll(pageable)
                .map(commentMapper::toDto);
    }
}
