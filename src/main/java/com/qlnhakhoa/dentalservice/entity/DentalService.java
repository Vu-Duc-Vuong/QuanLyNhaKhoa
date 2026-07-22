package com.qlnhakhoa.dentalservice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "dental_services")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DentalService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(length = 50)
    private String unit; // Ví dụ: Răng, Liệu trình, Hàm

    private String description;

    @Builder.Default
    private Boolean active = true;
}