package com.qlnhakhoa.auth.controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
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
public String loginPage(
        @RequestParam(value = "error", required = false) String error,
        Model model
) {

    if(error != null){
        model.addAttribute(
                "errorMessage",
                "Tài khoản hoặc mật khẩu không chính xác"
        );
    }

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

    System.out.println(request.getFullName());
    System.out.println(request.getUsername());
    System.out.println(request.getEmail());
    System.out.println(request.getPhone());

    authService.register(request);

    return "redirect:/login";
}

}