package com.qlnhakhoa.appointment.repository;

import com.qlnhakhoa.appointment.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDoctorIdAndWorkDate(Long doctorId, LocalDate workDate);
    List<Appointment> findByWorkDate(LocalDate workDate);
}