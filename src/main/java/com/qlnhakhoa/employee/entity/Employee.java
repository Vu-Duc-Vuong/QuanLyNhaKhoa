package com.qlnhakhoa.employee.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false, length = 30)
    private String role; // DOCTOR, STAFF, ADMIN, RECEPTIONIST

    @Column(unique = true, nullable = false, length = 15)
    private String phone;

    @Column(unique = true, length = 100)
    private String email;

    @Builder.Default
    private Boolean active = true;
}