package com.fedag.internship.UserServiceImpl;

import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.repository.UserRepository;
import com.fedag.internship.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith({MockitoExtension.class})
public class UserServiceImpl_createUser {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;

    @Test
    public void testPositive(){
        UserEntity userEntity=new UserEntity()
                .setEmail("smth@gmail.com")
                .setFirstName("name")
                .setLastName("last name");
       when(userRepository.save(userEntity)).thenReturn(userEntity);
       UserEntity result=userService.createUser(userEntity);
       assertEquals(userEntity,result);
       verify(userRepository,times(1)).save(any(UserEntity.class));
    }
}
