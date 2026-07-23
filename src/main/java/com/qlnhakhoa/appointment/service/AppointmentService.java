package com.qlnhakhoa.appointment.service;


import com.qlnhakhoa.appointment.entity.Appointment;
import com.qlnhakhoa.appointment.repository.AppointmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;



@Service
public class AppointmentService {


    @Autowired
    private AppointmentRepository appointmentRepository;



    // Lấy danh sách lịch hẹn
    public List<Appointment> getAllAppointments(){

        return appointmentRepository.findAll();

    }



    // Lưu lịch hẹn
    public Appointment saveAppointment(Appointment appointment){

        return appointmentRepository.save(appointment);

    }



    // Lấy lịch hẹn theo id
    public Appointment getAppointmentById(Long id){

        return appointmentRepository
                .findById(id)
                .orElse(null);

    }



    // Xóa lịch hẹn
    public void deleteAppointment(Long id){

        appointmentRepository.deleteById(id);

    }



    // Kiểm tra trùng lịch
    public boolean checkDuplicate(
            LocalDate date,
            LocalTime time){


        return appointmentRepository
                .existsByAppointmentDateAndAppointmentTime(
                        date,
                        time
                );

    }



    // Tìm kiếm lịch hẹn
    public List<Appointment> search(String keyword){

        return appointmentRepository
                .findByAppointmentCodeContainingIgnoreCaseOrPatientFullNameContainingIgnoreCase(
                        keyword,
                        keyword
                );

    }



    // Cập nhật trạng thái
    public void updateStatus(Long id, String status){

        Appointment appointment = getAppointmentById(id);

        if(appointment != null){

            appointment.setStatus(status);

            appointmentRepository.save(appointment);

        }

    }


}