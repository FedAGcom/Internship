package com.fedag.internship.CommentServiceImpl;

import com.fedag.internship.domain.entity.CommentEntity;
import com.fedag.internship.repository.CommentRepository;
import com.fedag.internship.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * class CommentServiceImpl_getAllComments
 *
 * @author damir.iusupov
 * @since 2022-06-07
 */
@ExtendWith(MockitoExtension.class)
public class CommentServiceImpl_findAll {
    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentRepository commentRepository;

    @Test
    public void testPositive() {
        CommentEntity comment1 = new CommentEntity().setText("some text # 1");
        CommentEntity comment2 = new CommentEntity().setText("some text # 2");
        CommentEntity comment3 = new CommentEntity().setText("some text # 3");
        Page<CommentEntity> page = new PageImpl<>(List.of(comment1, comment2, comment3));
        when(commentRepository.findAll(Pageable.ofSize(5))).thenReturn(page);
        Page<CommentEntity> result = commentService.findAll(Pageable.ofSize(5));
        assertEquals(3, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals("some text # 1", result.getContent().get(0).getText());
        assertEquals("some text # 2", result.getContent().get(1).getText());
        assertEquals("some text # 3", result.getContent().get(2).getText());
        verify(commentRepository, times(1)).findAll(any(Pageable.class));
    }
}
