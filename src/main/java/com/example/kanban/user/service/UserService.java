package com.example.kanban.user.service;

import com.example.kanban.user.dto.RegisterRequestDto;
import com.example.kanban.user.dto.UserResponseDto;
import com.example.kanban.user.model.Role;
import com.example.kanban.user.model.User;
import com.example.kanban.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User register(RegisterRequestDto requestDto) {
        Optional<User> isUsernameUse = userRepository.findByLogin(requestDto.getLogin());
        if (isUsernameUse.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username is already used");
        }

        User user = new User();
        user.setRole(Role.USER);
        user.setLogin(requestDto.getLogin());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        return userRepository.save(user);
    }

    public UserResponseDto login(RegisterRequestDto requestDto) {
        User user = userRepository.findByLogin(requestDto.getLogin())
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "User not found"));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid password"
            );
        }

        return new UserResponseDto(user.getLogin(), user.getRole(), user.getId());
    }
}
