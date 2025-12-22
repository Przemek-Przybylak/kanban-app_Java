package com.example.kanban.user.controller;

import com.example.kanban.user.dto.RegisterRequestDto;
import com.example.kanban.user.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
        public void register(@RequestBody RegisterRequestDto request) {
            userService.register(request);
        }
}
