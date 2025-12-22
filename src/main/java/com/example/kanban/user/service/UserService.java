package com.example.kanban.user.service;

import com.example.kanban.user.dto.RegisterRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Transactional
    public void register(RegisterRequestDto requestDto) {

    }
}
