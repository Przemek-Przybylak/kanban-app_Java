package com.example.kanban.user.service;

import com.example.kanban.user.dto.LoginRequestDto;
import com.example.kanban.user.dto.RegisterRequestDto;
import com.example.kanban.user.dto.UserResponseDto;
import com.example.kanban.user.model.Role;
import com.example.kanban.user.model.User;
import com.example.kanban.user.repository.UserRepository;
import com.example.kanban.user.util.JwtUtil;
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
        Optional<User> isUsernameUse = userRepository.findByLogin(requestDto.login());
        if (isUsernameUse.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username is already used");
        }

        User user = new User();
        user.setRole(Role.USER);
        user.setLogin(requestDto.login());
        user.setPassword(passwordEncoder.encode(requestDto.password()));

        return userRepository.save(user);
    }

    public UserResponseDto login(LoginRequestDto requestDto) {
        User user = userRepository.findByLogin(requestDto.login())
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "User not found"));

        if (!passwordEncoder.matches(requestDto.password(), user.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid password"
            );
        }

        String token = JwtUtil.generateToken(user.getLogin());

        return new UserResponseDto(token, user.getId(), user.getRole(), user.getLogin());
    }
}
