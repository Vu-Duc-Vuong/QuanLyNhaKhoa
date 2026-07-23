package com.qlnhakhoa.employee.dto;

import com.qlnhakhoa.employee.entity.Role;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignRoleRequestDTO {

    private Role newRole; // LE_TAN, BAC_SI, ADMIN
}