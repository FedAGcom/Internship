package com.fedag.internship.CommentServiceImpl;

import com.fedag.internship.domain.entity.CommentEntity;
import com.fedag.internship.domain.exception.EntityNotFoundException;
import com.fedag.internship.domain.mapper.CommentMapper;
import com.fedag.internship.repository.CommentRepository;
import com.fedag.internship.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * class CommentServiceImpl_updateComment
 *
 * @author damir.iusupov
 * @since 2022-06-07
 */
@ExtendWith(MockitoExtension.class)
public class CommentServiceImpl_update {
    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private CommentMapper commentMapper;

    @Test
    public void testCommentNotFound() {
        Long id = anyLong();
        CommentEntity commentEntity = new CommentEntity();
        when(commentRepository.findById(id)).thenReturn(Optional.empty());
        try {
            commentService.update(id, commentEntity);
        } catch (EntityNotFoundException exception) {
            assertEquals(String.format("%s with %s: %s not found", "Comment", "id", id),
                    exception.getMessage());
            verify(commentMapper, times(0)).merge(any(CommentEntity.class), any(CommentEntity.class));
            verify(commentRepository, times(0)).save(any(CommentEntity.class));
        }
    }

    @Test
    public void testPositive() {
        Long id = anyLong();
        CommentEntity oldComment = new CommentEntity().setText("some text # 1");
        CommentEntity newComment = new CommentEntity().setText("updated text");
        when(commentRepository.findById(id)).thenReturn(Optional.of(oldComment));
        when(commentMapper.merge(newComment, oldComment)).thenReturn(newComment);
        when(commentRepository.save(newComment)).thenReturn(newComment);
        CommentEntity result = commentService.update(id, newComment);
        assertEquals("updated text", result.getText());
        verify(commentMapper, times(1)).merge(any(CommentEntity.class), any(CommentEntity.class));
        verify(commentRepository, times(1)).save(any(CommentEntity.class));
    }
}
