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



        if(userRepository.existsByUsername(request.getUsername())){

            throw new RuntimeException("Tài khoản đã tồn tại");

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



        // Role mặc định
        user.setRole(Role.PATIENT);



        // trạng thái chờ admin duyệt
        user.setStatus("PENDING");



        // chưa được đăng nhập
        user.setEnabled(false);



        userRepository.save(user);

    }


}