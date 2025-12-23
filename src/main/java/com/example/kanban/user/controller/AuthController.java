package com.example.kanban.user.controller;

import com.example.kanban.user.dto.RegisterRequestDto;
import com.example.kanban.user.model.User;
import com.example.kanban.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody RegisterRequestDto request) {
        userService.register(request);
    }

    @PostMapping("/login")
    public User login(@RequestBody RegisterRequestDto request) {
        return userService.login(request);
    }
}
