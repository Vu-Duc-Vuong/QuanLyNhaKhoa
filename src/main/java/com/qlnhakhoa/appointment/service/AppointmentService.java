package com.qlnhakhoa.appointment.service;


import com.qlnhakhoa.appointment.entity.Appointment;
import com.qlnhakhoa.appointment.repository.AppointmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


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



}