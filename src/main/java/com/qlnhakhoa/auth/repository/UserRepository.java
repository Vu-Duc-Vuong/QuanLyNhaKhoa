package com.qlnhakhoa.auth.repository;

import com.qlnhakhoa.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository
        extends JpaRepository<User, Long> {


    Optional<User> findByUsername(String username);


    // Kiểm tra trùng tài khoản
    boolean existsByUsername(String username);


    // Kiểm tra trùng email
    boolean existsByEmail(String email);


    // Kiểm tra trùng số điện thoại
    boolean existsByPhone(String phone);


}