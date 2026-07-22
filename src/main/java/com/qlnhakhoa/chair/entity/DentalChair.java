package com.qlnhakhoa.chair.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "dental_chairs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DentalChair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 20)
    private String roomNumber;

    @Column(length = 20)
    private String status; // AVAILABLE, BUSY, MAINTENANCE
}