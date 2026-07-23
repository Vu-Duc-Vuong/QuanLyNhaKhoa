package com.qlnhakhoa.auth.service;

import com.qlnhakhoa.auth.entity.User;
import com.qlnhakhoa.auth.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService 
        implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {


        User user = userRepository
                .findByUsername(username)
                .orElseThrow(
                    () -> new UsernameNotFoundException(
                        "Không tìm thấy tài khoản"
                    )
                );


        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())

                .password(user.getPassword())

                // kiểm tra enabled
                .disabled(!user.isEnabled())

                .roles(user.getRole().name())

                .build();

    }

}