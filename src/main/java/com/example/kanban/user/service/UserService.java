package com.example.kanban.user.service;

import com.example.kanban.user.dto.RegisterRequestDto;
import com.example.kanban.user.model.Role;
import com.example.kanban.user.model.User;
import com.example.kanban.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User register(RegisterRequestDto requestDto) {
        Optional<User> isUsernameUse = userRepository.findByUsername(requestDto.getLogin());
        if (isUsernameUse.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username is already used");
        }

        User user = new User();
        user.setRole(Role.USER);
        user.setUsername(requestDto.getLogin());
        user.setPassword(requestDto.getPassword());

        return userRepository.save(user);
    }
}
