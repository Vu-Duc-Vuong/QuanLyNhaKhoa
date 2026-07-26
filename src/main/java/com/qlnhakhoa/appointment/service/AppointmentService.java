package com.qlnhakhoa.appointment.service;

import com.qlnhakhoa.appointment.entity.Appointment;
import com.qlnhakhoa.appointment.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    // Lấy danh sách lịch hẹn
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    // Lưu / cập nhật lịch hẹn
    public Appointment saveAppointment(Appointment appointment) {

        String code = appointment.getAppointmentCode().trim();

        if (!code.toUpperCase().startsWith("LH-")) {
            code = "LH-" + code;
        }

        appointment.setAppointmentCode(code);

        // Thêm mới
        if (appointment.getId() == null) {

            if (appointmentRepository.existsByAppointmentCode(code)) {
                throw new RuntimeException("Mã lịch hẹn đã tồn tại.");
            }

        } else {

            // Cập nhật
            Appointment old = appointmentRepository
                    .findById(appointment.getId())
                    .orElse(null);

            if (old != null
                    && !old.getAppointmentCode().equals(code)
                    && appointmentRepository.existsByAppointmentCode(code)) {

                throw new RuntimeException("Mã lịch hẹn đã tồn tại.");
            }
        }

        return appointmentRepository.save(appointment);
    }

    // Lấy lịch hẹn theo ID
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    // Xóa lịch hẹn
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    // Kiểm tra trùng lịch
    public boolean checkDuplicate(LocalDate date, LocalTime time) {
        return appointmentRepository.existsByAppointmentDateAndAppointmentTime(
                date,
                time
        );
    }

    // Tìm kiếm lịch hẹn
    public List<Appointment> search(String keyword) {
        return appointmentRepository
                .findByAppointmentCodeContainingIgnoreCaseOrPatientFullNameContainingIgnoreCase(
                        keyword,
                        keyword
                );
    }

    // Cập nhật trạng thái
    public void updateStatus(Long id, String status) {

        Appointment appointment = getAppointmentById(id);

        if (appointment != null) {
            appointment.setStatus(status);
            appointmentRepository.save(appointment);
        }
    }
        // Kiểm tra mã lịch hẹn đã tồn tại
    public boolean existsByAppointmentCode(String code) {

        return appointmentRepository
                .existsByAppointmentCode(code);

    }
}