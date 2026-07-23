package com.qlnhakhoa.auth.controller;

import com.qlnhakhoa.auth.dto.RegisterRequest;
import com.qlnhakhoa.auth.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    // Trang đăng nhập
    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    // Trang đăng ký
    @GetMapping("/register")
    public String registerPage(RegisterRequest request) {
        return "auth/register";
    }

    // Xử lý đăng ký
    @PostMapping("/register")
    public String register(
            @ModelAttribute RegisterRequest request) {

        authService.register(request);

        return "redirect:/login";
    }

}