package com.fedag.internship.CommentServiceImpl;

import com.fedag.internship.domain.entity.CommentEntity;
import com.fedag.internship.domain.entity.CompanyEntity;
import com.fedag.internship.domain.entity.TraineePositionEntity;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * class CommentServiceImpl_deleteComment
 *
 * @author damir.iusupov
 * @since 2022-06-07
 */
@ExtendWith(MockitoExtension.class)
public class CommentServiceImpl_deleteById {
    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private UserService userService;

    @Test
    public void testCommentNotFound() {
        Long id = anyLong();
        when(commentRepository.findById(id)).thenReturn(Optional.empty());
        try {
            commentService.deleteById(id);
        } catch (EntityNotFoundException exception) {
            assertEquals(String.format("%s with %s: %s not found", "Comment", "id", id),
                    exception.getMessage());
            verify(userService, times(0)).findById(anyLong());
            verify(commentRepository, times(0)).deleteById(anyLong());
        }
    }

    @Test
    public void testPositiveForCompanyComment() {
        Long id = anyLong();
        CommentEntity comment = new CommentEntity().setText("some text # 1");
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.addComments(comment);
        String email = "some1@email.com";
        String firstName = "some name";
        String lastName = "some surname";
        UserEntity user = new UserEntity()
                .setEmail(email)
                .setFirstName(firstName)
                .setLastName(lastName);
        user.addComments(comment);
        when(commentRepository.findById(id)).thenReturn(Optional.of(comment));
        commentService.deleteById(id);
        assertEquals(0, user.getComments().size());
        assertEquals(0, companyEntity.getComments().size());
        verify(commentRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void testPositiveForPositionComment() {
        Long id = anyLong();
        CommentEntity comment = new CommentEntity().setText("some text # 1");
        TraineePositionEntity traineePosition = new TraineePositionEntity();
        traineePosition.addComments(comment);
        String email = "some1@email.com";
        String firstName = "some name";
        String lastName = "some surname";
        UserEntity user = new UserEntity()
                .setEmail(email)
                .setFirstName(firstName)
                .setLastName(lastName);
        user.addComments(comment);
        when(commentRepository.findById(id)).thenReturn(Optional.of(comment));
        commentService.deleteById(id);
        assertEquals(0, user.getComments().size());
        assertEquals(0, traineePosition.getComments().size());
        verify(commentRepository, times(1)).deleteById(anyLong());
    }
}
