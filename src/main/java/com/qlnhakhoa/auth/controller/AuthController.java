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


        if(error != null){

            model.addAttribute(
                    "errorMessage",
                    "Tài khoản hoặc mật khẩu không chính xác"
            );

        }



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
    public String registerPage(Model model) {


        model.addAttribute(
                "registerRequest",
                new RegisterRequest()
        );


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


            return "redirect:/login?registerSuccess=true";


        } catch (RuntimeException e) {


            model.addAttribute(
                    "errorMessage",
                    e.getMessage()
            );


            model.addAttribute(
                    "registerRequest",
                    request
            );


            return "auth/register";

        }


    }


}