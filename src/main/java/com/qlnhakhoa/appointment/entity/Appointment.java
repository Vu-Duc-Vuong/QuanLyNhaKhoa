package com.qlnhakhoa.appointment.entity;

import com.qlnhakhoa.chair.entity.DentalChair;
import com.qlnhakhoa.employee.entity.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "doctor_schedules")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Employee doctor;

    @ManyToOne
    @JoinColumn(name = "chair_id", nullable = false)
    private DentalChair chair;

    @Column(nullable = false)
    private LocalDate workDate;

    @Column(nullable = false, length = 20)
    private String shift; // MORNING, AFTERNOON, EVENING

    private String note;
}