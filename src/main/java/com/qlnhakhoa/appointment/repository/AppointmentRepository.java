package com.qlnhakhoa.appointment.repository;


import com.qlnhakhoa.appointment.entity.Appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;



@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {


    // Lấy toàn bộ lịch hẹn của một bệnh nhân
    List<Appointment> findByPatientId(Long patientId);



    // Kiểm tra trùng lịch
    boolean existsByAppointmentDateAndAppointmentTime(
            LocalDate date,
            LocalTime time
    );



    // Kiểm tra trùng mã lịch hẹn
    boolean existsByAppointmentCode(String appointmentCode);



    // Lấy lịch theo ngày, sắp xếp giờ khám tăng dần
    List<Appointment> findByAppointmentDateOrderByAppointmentTimeAsc(
            LocalDate date
    );



    // Lấy tất cả lịch hẹn, sắp xếp theo ngày và giờ
    List<Appointment> findAllByOrderByAppointmentDateAscAppointmentTimeAsc();



    // Tìm theo trạng thái lịch hẹn
    List<Appointment> findByStatus(String status);



    // Lấy danh sách chờ khám trong ngày (tìm gần đúng)
    List<Appointment> findByAppointmentDateAndStatusContainingIgnoreCaseOrderByAppointmentTimeAsc(
            LocalDate date,
            String status
    );


    // Lấy danh sách theo ngày + trạng thái chính xác
    // DashboardController đang gọi hàm này
    List<Appointment> findByAppointmentDateAndStatusOrderByAppointmentTimeAsc(
            LocalDate date,
            String status
    );



    // Tìm kiếm theo mã lịch hoặc tên bệnh nhân
    List<Appointment> findByAppointmentCodeContainingIgnoreCaseOrPatientFullNameContainingIgnoreCase(
            String code,
            String name
    );

}