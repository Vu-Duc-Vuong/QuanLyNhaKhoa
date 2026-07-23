package com.qlnhakhoa.auth.service;


import com.qlnhakhoa.auth.dto.RegisterRequest;
import com.qlnhakhoa.auth.entity.Role;
import com.qlnhakhoa.auth.entity.User;
import com.qlnhakhoa.auth.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class AuthServiceImpl implements AuthService {



    @Autowired
    private UserRepository userRepository;



    @Autowired
    private PasswordEncoder passwordEncoder;




    @Override
    public void register(RegisterRequest request) {


        // Kiểm tra trùng username
        if(userRepository.existsByUsername(request.getUsername())){

            throw new RuntimeException(
                    "Tài khoản đã tồn tại"
            );

        }


        // Kiểm tra trùng email
        if(userRepository.existsByEmail(request.getEmail())){

            throw new RuntimeException(
                    "Email đã được sử dụng"
            );

        }


        // Kiểm tra trùng số điện thoại
        if(userRepository.existsByPhone(request.getPhone())){

            throw new RuntimeException(
                    "Số điện thoại đã được sử dụng"
            );

        }



        User user = new User();



        user.setFullName(
                request.getFullName()
        );


        user.setUsername(
                request.getUsername()
        );


        user.setEmail(
                request.getEmail()
        );


        user.setPhone(
                request.getPhone()
        );



        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );



        // Người đăng ký mặc định là bệnh nhân
        user.setRole(Role.PATIENT);



        // Trạng thái tài khoản
        user.setStatus("ACTIVE");



        // Cho phép đăng nhập
        user.setEnabled(true);



        userRepository.save(user);

    }


}