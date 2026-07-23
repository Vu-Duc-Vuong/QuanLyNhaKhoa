package com.qlnhakhoa.auth.config;

import com.qlnhakhoa.auth.entity.Role;
import com.qlnhakhoa.auth.entity.User;
import com.qlnhakhoa.auth.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {


    @Bean
    CommandLineRunner initAdmin(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {


        return args -> {


            if (!userRepository.existsByUsername("admin")) {


                User admin = new User();


                admin.setFullName("Administrator");

                admin.setUsername("admin");

                admin.setEmail("admin@gmail.com");

                admin.setPhone("0123456789");


                admin.setPassword(
                        passwordEncoder.encode("123456")
                );


                admin.setRole(Role.ADMIN);


                admin.setStatus("ACTIVE");


                admin.setEnabled(true);


                userRepository.save(admin);


            }


        };

    }

}