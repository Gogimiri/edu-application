package com.project.edu_service.controllers;

import com.project.edu_service.Dtos.UserDto;
import com.project.edu_service.entityes.Role;
import com.project.edu_service.entityes.Users;
import com.project.edu_service.exceptions.UserAlreadyExistsException;
import com.project.edu_service.mappers.UserMapper;
import com.project.edu_service.security.JwtUtil;
import com.project.edu_service.service.UserService;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/registration")
    public Map<String, String> performRegistration(@RequestBody UserDto userDto) {

        if (userService.userExists(userDto.getUsername())) {
            logger.warn("User already exists: {}", userDto.getUsername());
            throw new UserAlreadyExistsException("Пользователь с таким именем уже существует");
        }
        logger.info("authDto insert: {}", userDto);
        Users users = userMapper.toEntity(userDto);
        users.setRole(Role.USER);
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        logger.info("User before set role: {}", users);
        userService.registration(users);
        return Map.of("jwt",jwtUtil.generateToken(users.getUsername(), users.getRole()));
    }

    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody UserDto userDto) {
        UsernamePasswordAuthenticationToken passwordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword());
        try {
            authenticationManager.authenticate(passwordAuthenticationToken);
        } catch (BadCredentialsException e) {
            return Map.of("message", "incorrect username or password");
        }
        Users users = userService.getByUsername(userDto.getUsername());
        return Map.of("jwt", jwtUtil.generateToken(userDto.getUsername(), users.getRole()));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
