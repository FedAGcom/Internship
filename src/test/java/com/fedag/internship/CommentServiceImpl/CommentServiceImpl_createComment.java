package com.fedag.internship.CommentServiceImpl;

import com.fedag.internship.domain.entity.CommentEntity;
import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.domain.exception.EntityNotFoundException;
import com.fedag.internship.repository.CommentRepository;
import com.fedag.internship.service.UserService;
import com.fedag.internship.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * class CommentServiceImpl_createComment
 *
 * @author damir.iusupov
 * @since 2022-06-07
 */
@ExtendWith(MockitoExtension.class)
public class CommentServiceImpl_createComment {
    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private UserService userService;

    @Test
    public void testUserNotFound() {
        Long id = anyLong();
        CommentEntity commentEntity = new CommentEntity();
        when(userService.getUserById(id)).thenThrow(new EntityNotFoundException("User", "id", id));
        try {
            commentService.createComment(id, commentEntity);
        } catch (EntityNotFoundException exception) {
            assertEquals(String.format("%s with %s: %s not found", "User", "id", id),
                    exception.getMessage());
            verify(commentRepository, times(0)).save(any(CommentEntity.class));
        }
    }

    @Test
    public void testPositive() {
        Long id = anyLong();
        String email = "some1@email.com";
        String firstName = "some name";
        String lastName = "some surname";
        UserEntity userEntity = new UserEntity()
                .setEmail(email)
                .setFirstName(firstName)
                .setLastName(lastName);
        CommentEntity commentEntity = new CommentEntity().setText("some text # 1");
        when(userService.getUserById(id)).thenReturn(userEntity);
        when(commentRepository.save(commentEntity)).thenReturn(commentEntity);
        CommentEntity result = commentService.createComment(id, commentEntity);
        assertEquals("some text # 1", result.getText());
        assertEquals(userEntity, result.getUser());
        verify(commentRepository, times(1)).save(any(CommentEntity.class));
    }
}
