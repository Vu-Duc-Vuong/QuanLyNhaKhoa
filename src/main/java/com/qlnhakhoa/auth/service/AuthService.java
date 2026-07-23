package com.qlnhakhoa.auth.service;

import com.qlnhakhoa.auth.dto.LoginRequest;
import com.qlnhakhoa.auth.dto.RegisterRequest;
import com.qlnhakhoa.auth.entity.User;
import jakarta.servlet.http.HttpSession;

public interface AuthService {

    String SESSION_USER_KEY = "currentUser";

    User register(RegisterRequest request);

    User login(LoginRequest request);

    void logout(HttpSession session);
}