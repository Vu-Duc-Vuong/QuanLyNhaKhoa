package com.qlnhakhoa.appointment.repository;


import com.qlnhakhoa.appointment.entity.Appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Repository
public interface AppointmentRepository 
        extends JpaRepository<Appointment, Long> {



    // Kiểm tra trùng lịch
    boolean existsByAppointmentDateAndAppointmentTime(
            LocalDate date,
            LocalTime time
    );



    // Tìm lịch theo ngày
    List<Appointment> findByAppointmentDate(
            LocalDate date
    );



    // Tìm kiếm theo mã lịch hoặc tên bệnh nhân
    List<Appointment> findByAppointmentCodeContainingIgnoreCaseOrPatientFullNameContainingIgnoreCase(
            String code,
            String name
    );


}