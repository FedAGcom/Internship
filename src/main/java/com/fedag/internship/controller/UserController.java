package com.fedag.internship.controller;

import com.fedag.internship.domain.dto.UserRequest;
import com.fedag.internship.domain.dto.UserRequestUpdate;
import com.fedag.internship.domain.dto.UserResponse;
import com.fedag.internship.domain.mapper.UserMapper;
import com.fedag.internship.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        UserResponse userResponse = Optional.of(id)
                .map(userService::getUserById)
                .map(userMapper::toResponse)
                .orElseThrow();
        return new ResponseEntity<>(userResponse, OK);
    }

    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAllUsers(@PageableDefault(size = 5) Pageable pageable) {
        Page<UserResponse> users = userService.getAllUsers(pageable)
                .map(userMapper::toResponse);
        return new ResponseEntity<>(users, OK);
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest) {
        UserResponse userResponse = Optional.ofNullable(userRequest)
                .map(userMapper::fromRequest)
                .map(userService::createUser)
                .map(userMapper::toResponse)
                .orElseThrow();
        return new ResponseEntity<>(userResponse, CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id,
                                        @RequestBody @Valid UserRequestUpdate userRequestUpdate) {
        UserResponse userResponse = Optional.ofNullable(userRequestUpdate)
                .map(userMapper::fromRequestUpdate)
                .map(user -> userService.updateUser(id, user))
                .map(userMapper::toResponse)
                .orElseThrow();
        return new ResponseEntity<>(userResponse, OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(OK);
    }
}
