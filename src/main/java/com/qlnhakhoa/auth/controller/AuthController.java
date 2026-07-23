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
            @RequestParam(value = "registerSuccess", required = false) String registerSuccess,
            Model model
    ) {


        // Sai tài khoản hoặc mật khẩu
        if(error != null){

            model.addAttribute(
                    "errorMessage",
                    "Tài khoản hoặc mật khẩu không chính xác"
            );

        }



        // Đăng ký thành công
        if(registerSuccess != null){

            model.addAttribute(
                    "successMessage",
                    "Đăng ký tài khoản thành công"
            );

        }


        return "auth/login";

    }





    // Trang đăng ký
    @GetMapping("/register")
    public String registerPage(
            RegisterRequest request
    ) {

        return "auth/register";

    }





    // Xử lý đăng ký
    @PostMapping("/register")
    public String register(
            @ModelAttribute RegisterRequest request,
            Model model
    ) {


        try {


            authService.register(request);


            // chuyển sang login + thông báo thành công
            return "redirect:/login?registerSuccess=true";


        } catch (RuntimeException e) {


            // lỗi trùng tài khoản/email/sđt
            model.addAttribute(
                    "errorMessage",
                    e.getMessage()
            );


            return "auth/register";

        }


    }


}