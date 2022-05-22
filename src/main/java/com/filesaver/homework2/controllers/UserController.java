package com.filesaver.homework2.controllers;

import com.filesaver.homework2.domain.User;
import com.filesaver.homework2.repositories.db.UserRepository;
import com.filesaver.homework2.requests.user.CreateRequest;
import com.filesaver.homework2.requests.user.UpdateRequest;
import com.filesaver.homework2.responses.UserResponse;
import com.filesaver.homework2.services.UserService;
import io.micrometer.core.annotation.Timed;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private final UserRepository userRepository;

    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostMapping("/user")
    @Timed
    public UserResponse store(@RequestBody CreateRequest request) {
        return new UserResponse(userService.create(request));
    }

    @GetMapping("/user/all")
    @Timed
    public List<UserResponse> index() {
        return userRepository.findAll().stream().map(UserResponse::new).collect(Collectors.toList());
    }

    @PatchMapping("/user/{id}")
    @Timed
    public UserResponse update(@PathVariable String id, @RequestBody UpdateRequest updateRequest) {
        User user = userRepository.findById(Long.parseLong(id)).orElseThrow();

        user.setEmail(updateRequest.getEmail());
        user.setName(updateRequest.getName());
        user.setPhone(updateRequest.getPhone());

        return new UserResponse(userRepository.update(user));
    }

    @GetMapping("/user/{id}")
    @Timed
    public UserResponse get(@PathVariable String id) {
        return new UserResponse(userRepository.findById(Long.parseLong(id)).orElseThrow());
    }

    @DeleteMapping("/user/{id}")
    @Timed
    public void delete(@PathVariable String id) {
        userRepository.delete(userRepository.findById(Long.parseLong(id)).orElseThrow());
    }
}
