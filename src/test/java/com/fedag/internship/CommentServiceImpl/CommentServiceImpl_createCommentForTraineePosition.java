package com.fedag.internship.CommentServiceImpl;

import com.fedag.internship.domain.entity.CommentEntity;
import com.fedag.internship.domain.entity.TraineePositionEntity;
import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.domain.exception.EntityNotFoundException;
import com.fedag.internship.repository.CommentRepository;
import com.fedag.internship.service.TraineePositionService;
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
 * class CommentServiceImpl_createCommentForTraineePosition
 *
 * @author damir.iusupov
 * @since 2022-06-07
 */
@ExtendWith(MockitoExtension.class)
public class CommentServiceImpl_createCommentForTraineePosition {
    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private UserService userService;
    @Mock
    private TraineePositionService traineePositionService;

    @Test
    public void testUserNotFound() {
        Long userId = 123L;
        Long traineePositionId = 321L;
        CommentEntity commentEntity = new CommentEntity();
        when(userService.getUserById(userId)).thenThrow(new EntityNotFoundException("User", "id", userId));
        try {
            commentService.createCommentForTraineePosition(userId, traineePositionId, commentEntity);
        } catch (EntityNotFoundException exception) {
            assertEquals(String.format("%s with %s: %s not found", "User", "id", userId),
                    exception.getMessage());
            verify(commentRepository, times(0)).save(any(CommentEntity.class));
            verify(traineePositionService, times(0)).getPositionById(anyLong());
        }
    }

    @Test
    public void testTraineePositionNotFound() {
        Long userId = 123L;
        Long traineePositionId = 321L;
        CommentEntity commentEntity = new CommentEntity();
        String email = "some1@email.com";
        String firstName = "some name";
        String lastName = "some surname";
        UserEntity userEntity = new UserEntity()
                .setEmail(email)
                .setFirstName(firstName)
                .setLastName(lastName);
        when(userService.getUserById(userId)).thenReturn(userEntity);
        when(traineePositionService.getPositionById(traineePositionId))
                .thenThrow(new EntityNotFoundException("TraineePosition", "id", traineePositionId));
        try {
            commentService.createCommentForTraineePosition(userId, traineePositionId, commentEntity);
        } catch (EntityNotFoundException exception) {
            assertEquals(String.format("%s with %s: %s not found", "TraineePosition", "id", traineePositionId),
                    exception.getMessage());
            verify(commentRepository, times(0)).save(any(CommentEntity.class));
        }
    }

    @Test
    public void testPositive() {
        Long userId = 123L;
        Long traineePositionId = 321L;
        String email = "some1@email.com";
        String firstName = "some name";
        String lastName = "some surname";
        UserEntity userEntity = new UserEntity()
                .setEmail(email)
                .setFirstName(firstName)
                .setLastName(lastName);
        String name = "some name";
        String empPos = "some position";
        TraineePositionEntity traineePosition = new TraineePositionEntity()
                .setName(name)
                .setEmployeePosition(empPos);
        CommentEntity commentEntity = new CommentEntity()
                .setText("some text # 1")
                .setRating(6D);
        when(userService.getUserById(userId)).thenReturn(userEntity);
        when(traineePositionService.getPositionById(traineePositionId)).thenReturn(traineePosition);
        when(commentRepository.save(commentEntity)).thenReturn(commentEntity);
        CommentEntity result = commentService.createCommentForTraineePosition(userId, traineePositionId, commentEntity);
        assertEquals("some text # 1", result.getText());
        assertEquals(6D, result.getRating());
        assertEquals(userEntity, result.getUser());
        assertEquals(traineePosition, result.getTraineePosition());
        verify(commentRepository, times(1)).save(any(CommentEntity.class));
    }
}
