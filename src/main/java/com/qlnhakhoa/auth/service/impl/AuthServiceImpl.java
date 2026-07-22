package com.qlnhakhoa.auth.service.impl;

import com.qlnhakhoa.auth.dto.LoginRequest;
import com.qlnhakhoa.auth.dto.RegisterRequest;
import com.qlnhakhoa.auth.entity.Role;
import com.qlnhakhoa.auth.entity.User;
import com.qlnhakhoa.auth.repository.UserRepository;
import com.qlnhakhoa.auth.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AuthServiceImpl implements AuthService {

    public static final String SESSION_USER_KEY = "currentUser";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(RegisterRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Register request is required");
        }
        if (!StringUtils.hasText(request.getPhone())) {
            throw new IllegalArgumentException("Phone is required");
        }
        if (!StringUtils.hasText(request.getPassword())) {
            throw new IllegalArgumentException("Password is required");
        }
        if (request.getConfirmPassword() != null && !request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        if (userRepository.existsByPhone(request.getPhone())) {
            throw new IllegalArgumentException("Phone already exists");
        }
        if (StringUtils.hasText(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole() != null ? request.getRole() : Role.PATIENT);
        user.setStatus(request.getStatus() != null ? request.getStatus() : Boolean.TRUE);
        return userRepository.save(user);
    }

    @Override
    public User login(LoginRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Login request is required");
        }
        if (!StringUtils.hasText(request.getPhone()) || !StringUtils.hasText(request.getPassword())) {
            throw new IllegalArgumentException("Phone and password are required");
        }

        User user = userRepository.findByPhone(request.getPhone())
                .orElseThrow(() -> new IllegalArgumentException("Invalid phone or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid phone or password");
        }
        if (Boolean.FALSE.equals(user.getStatus())) {
            throw new IllegalStateException("Account is inactive");
        }
        return user;
    }

    @Override
    public void logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
    }
}