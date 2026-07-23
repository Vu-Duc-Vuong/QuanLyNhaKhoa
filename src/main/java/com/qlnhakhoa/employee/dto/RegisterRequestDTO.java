package com.qlnhakhoa.employee.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDTO {

    private String fullName;
    private String phone;
    private String email;
    private String password;
}