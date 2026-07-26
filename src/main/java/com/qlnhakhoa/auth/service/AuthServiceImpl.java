package com.qlnhakhoa.auth.service;


import com.qlnhakhoa.auth.dto.RegisterRequest;
import com.qlnhakhoa.auth.entity.Role;
import com.qlnhakhoa.auth.entity.User;
import com.qlnhakhoa.auth.repository.UserRepository;

import com.qlnhakhoa.clinic.entity.Clinic;
import com.qlnhakhoa.clinic.repository.ClinicRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class AuthServiceImpl implements AuthService {



    @Autowired
    private UserRepository userRepository;



    @Autowired
    private ClinicRepository clinicRepository;



    @Autowired
    private PasswordEncoder passwordEncoder;





    @Override
    public void register(RegisterRequest request) {


        // Kiểm tra username
        if(userRepository.existsByUsername(request.getUsername())){

            throw new RuntimeException(
                    "Tài khoản đã tồn tại"
            );

        }



        // Kiểm tra email
        if(userRepository.existsByEmail(request.getEmail())){

            throw new RuntimeException(
                    "Email đã được sử dụng"
            );

        }



        // Kiểm tra số điện thoại
        if(userRepository.existsByPhone(request.getPhone())){

            throw new RuntimeException(
                    "Số điện thoại đã được sử dụng"
            );

        }



        /*
         * Tạo Nha khoa mới cho Admin
         */

        Clinic clinic = new Clinic();


        clinic.setClinicCode(
                "NK" + System.currentTimeMillis()
        );


        clinic.setClinicName(
                "Nha khoa của " + request.getFullName()
        );


        clinic.setStatus(
                "ACTIVE"
        );


        clinicRepository.save(clinic);





        /*
         * Tạo tài khoản Admin
         */

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



        // Chỉ còn ADMIN
        user.setRole(
                Role.ADMIN
        );



        user.setStatus(
                "ACTIVE"
        );



        user.setEnabled(
                true
        );



        // Gán User vào Nha khoa
        user.setClinic(
                clinic
        );



        userRepository.save(user);

    }


}