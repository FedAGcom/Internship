package com.fedag.internship.service;

import com.fedag.internship.dto.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserById(long id);

    User addUser(User user);

    void deleteUser(long id);

    User editUser(User user);
}
