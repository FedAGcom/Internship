package com.fedag.internship.UserServiceImpl;

import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.repository.UserRepository;
import com.fedag.internship.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceImpl_deleteUser {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;

    @Test
    public void testPositive(){
        Long id= ArgumentMatchers.anyLong();
        UserEntity userEntity=new UserEntity()
                .setEmail("smth@gmail.com")
                .setFirstName("name")
                .setLastName("last name")
                .setId(id);


    }
}
