package com.fedag.internship.CommentServiceImpl;

import com.fedag.internship.domain.entity.CommentEntity;
import com.fedag.internship.domain.entity.CompanyEntity;
import com.fedag.internship.domain.entity.TraineePositionEntity;
import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.domain.exception.EntityNotFoundException;
import com.fedag.internship.repository.CommentRepository;
import com.fedag.internship.service.CompanyService;
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
 * class CommentServiceImpl_createCommentForCompany
 *
 * @author damir.iusupov
 * @since 2022-06-07
 */
@ExtendWith(MockitoExtension.class)
public class CommentServiceImpl_createCommentForCompany {
    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private UserService userService;
    @Mock
    private CompanyService companyService;

    @Test
    public void testUserNotFound() {
        Long userId = 123L;
        Long companyId = 321L;
        CommentEntity commentEntity = new CommentEntity();
        when(userService.getUserById(userId)).thenThrow(new EntityNotFoundException("User", "id", userId));
        try {
            commentService.createCommentForCompany(userId, companyId, commentEntity);
        } catch (EntityNotFoundException exception) {
            assertEquals(String.format("%s with %s: %s not found", "User", "id", userId),
                    exception.getMessage());
            verify(commentRepository, times(0)).save(any(CommentEntity.class));
            verify(companyService, times(0)).getCompanyById(anyLong());
        }
    }

    @Test
    public void testCompanyNotFound() {
        Long userId = 123L;
        Long companyId = 321L;
        CommentEntity commentEntity = new CommentEntity();
        String email = "some1@email.com";
        String firstName = "some name";
        String lastName = "some surname";
        UserEntity userEntity = new UserEntity()
                .setEmail(email)
                .setFirstName(firstName)
                .setLastName(lastName);
        when(userService.getUserById(userId)).thenReturn(userEntity);
        when(companyService.getCompanyById(companyId))
                .thenThrow(new EntityNotFoundException("Company", "id", companyId));
        try {
            commentService.createCommentForCompany(userId, companyId, commentEntity);
        } catch (EntityNotFoundException exception) {
            assertEquals(String.format("%s with %s: %s not found", "Company", "id", companyId),
                    exception.getMessage());
            verify(commentRepository, times(0)).save(any(CommentEntity.class));
        }
    }

    @Test
    public void testPositive() {
        Long userId = 123L;
        Long companyId = 321L;
        String email = "some1@email.com";
        String firstName = "some name";
        String lastName = "some surname";
        UserEntity userEntity = new UserEntity()
                .setEmail(email)
                .setFirstName(firstName)
                .setLastName(lastName);
        String name = "some name";
        String description = "some description";
        CompanyEntity companyEntity = new CompanyEntity()
                .setName(name)
                .setDescription(description);
        CommentEntity commentEntity = new CommentEntity()
                .setText("some text # 1")
                .setRating(6D);
        when(userService.getUserById(userId)).thenReturn(userEntity);
        when(companyService.getCompanyById(companyId)).thenReturn(companyEntity);
        when(commentRepository.save(commentEntity)).thenReturn(commentEntity);
        CommentEntity result = commentService.createCommentForCompany(userId, companyId, commentEntity);
        assertEquals("some text # 1", result.getText());
        assertEquals(6D, result.getRating());
        assertEquals(userEntity, result.getUser());
        assertEquals(companyEntity, result.getCompany());
        verify(commentRepository, times(1)).save(any(CommentEntity.class));
    }
}
