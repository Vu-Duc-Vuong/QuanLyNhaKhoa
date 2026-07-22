package com.qlnhakhoa.medicine.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "medicines")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 20)
    private String unit; // Viên, Hộp, Chai

    @Column(nullable = false)
    private Integer stockQuantity;

    @Column(nullable = false)
    private BigDecimal price;

    private String usageInstructions;
}