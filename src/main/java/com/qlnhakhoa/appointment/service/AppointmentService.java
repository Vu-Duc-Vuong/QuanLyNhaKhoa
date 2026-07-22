package com.qlnhakhoa.appointment.service;

import com.qlnhakhoa.appointment.dto.AppointmentRequestDTO;
import com.qlnhakhoa.appointment.dto.AppointmentResponseDTO;
import com.qlnhakhoa.appointment.entity.Appointment;
import com.qlnhakhoa.appointment.repository.AppointmentRepository;
import com.qlnhakhoa.chair.entity.DentalChair;
import com.qlnhakhoa.chair.repository.DentalChairRepository;
import com.qlnhakhoa.employee.entity.Employee;
import com.qlnhakhoa.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DentalChairRepository chairRepository;

    public List<AppointmentResponseDTO> getAll() {
        return appointmentRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<AppointmentResponseDTO> getByDate(LocalDate date) {
        return appointmentRepository.findByWorkDate(date).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public AppointmentResponseDTO getById(Long id) {
        Appointment app = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch hẹn với ID: " + id));
        return convertToResponseDTO(app);
    }

    public AppointmentResponseDTO create(AppointmentRequestDTO dto) {
        Employee doctor = employeeRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Bác sĩ ID: " + dto.getDoctorId()));

        DentalChair chair = chairRepository.findById(dto.getChairId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Ghế nha ID: " + dto.getChairId()));

        Appointment appointment = Appointment.builder()
                .doctor(doctor)
                .chair(chair)
                .workDate(dto.getWorkDate())
                .shift(dto.getShift())
                .note(dto.getNote())
                .build();

        return convertToResponseDTO(appointmentRepository.save(appointment));
    }

    public AppointmentResponseDTO update(Long id, AppointmentRequestDTO dto) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch hẹn với ID: " + id));

        Employee doctor = employeeRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Bác sĩ ID: " + dto.getDoctorId()));

        DentalChair chair = chairRepository.findById(dto.getChairId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Ghế nha ID: " + dto.getChairId()));

        appointment.setDoctor(doctor);
        appointment.setChair(chair);
        appointment.setWorkDate(dto.getWorkDate());
        appointment.setShift(dto.getShift());
        appointment.setNote(dto.getNote());

        return convertToResponseDTO(appointmentRepository.save(appointment));
    }

    public void delete(Long id) {
        appointmentRepository.deleteById(id);
    }

    // Helper method chuyển từ Entity sang ResponseDTO
    private AppointmentResponseDTO convertToResponseDTO(Appointment entity) {
        return AppointmentResponseDTO.builder()
                .id(entity.getId())
                .doctorId(entity.getDoctor().getId())
                .doctorName(entity.getDoctor().getFullName())
                .chairId(entity.getChair().getId())
                .chairName(entity.getChair().getName())
                .roomNumber(entity.getChair().getRoomNumber())
                .workDate(entity.getWorkDate())
                .shift(entity.getShift())
                .note(entity.getNote())
                .build();
    }
}