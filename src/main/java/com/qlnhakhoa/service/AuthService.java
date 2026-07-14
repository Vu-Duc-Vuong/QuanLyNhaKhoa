package com.qlnhakhoa.service;

import com.qlnhakhoa.dto.LoginRequest;
import com.qlnhakhoa.dto.RegisterRequest;
import com.qlnhakhoa.entity.User;
import jakarta.servlet.http.HttpSession;

public interface AuthService {

    String SESSION_USER_KEY = "currentUser";

    User register(RegisterRequest request);

    User login(LoginRequest request);

    void logout(HttpSession session);
}
