package com.fedag.internship.CommentServiceImpl;

import com.fedag.internship.domain.entity.CommentEntity;
import com.fedag.internship.domain.exception.EntityNotFoundException;
import com.fedag.internship.repository.CommentRepository;
import com.fedag.internship.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * class CommentServiceImpl_getCommentById
 *
 * @author damir.iusupov
 * @since 2022-06-07
 */
@ExtendWith(MockitoExtension.class)
public class CommentServiceImpl_findById {
    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentRepository commentRepository;

    @Test
    public void testCommentNotFound() {
        Long id = anyLong();
        when(commentRepository.findById(id)).thenReturn(Optional.empty());
        try {
            commentService.findById(id);
        } catch (EntityNotFoundException exception) {
            assertEquals(String.format("%s with %s: %s not found", "Comment", "id", id),
                    exception.getMessage());
        }
    }

    @Test
    public void testPositive() {
        Long id = anyLong();
        CommentEntity comment = new CommentEntity().setText("some text # 1");
        when(commentRepository.findById(id)).thenReturn(Optional.of(comment));
        CommentEntity result = commentService.findById(id);
        assertEquals("some text # 1", result.getText());
        verify(commentRepository, times(1)).findById(anyLong());
    }
}
