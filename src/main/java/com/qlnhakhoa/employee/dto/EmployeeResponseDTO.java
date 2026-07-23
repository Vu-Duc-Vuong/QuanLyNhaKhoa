package com.qlnhakhoa.employee.dto;

import com.qlnhakhoa.employee.entity.Role;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponseDTO {

    private Long id;
    private String fullName;
    private String phone;
    private String email;
    private Role role;
}