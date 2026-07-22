package com.qlnhakhoa.appointment.entity;


import com.qlnhakhoa.patient.entity.Patient;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Table(name = "appointments")
public class Appointment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    // Mã lịch hẹn
    @Column(unique = true, nullable = false)
    private String appointmentCode;



    // Liên kết bệnh nhân
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;



    // Ngày hẹn
    private LocalDate appointmentDate;



    // Giờ hẹn
    private LocalTime appointmentTime;



    // Trạng thái
    private String status;



    // Ghi chú
    private String note;



    public Appointment() {
    }



    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getAppointmentCode() {
        return appointmentCode;
    }


    public void setAppointmentCode(String appointmentCode) {
        this.appointmentCode = appointmentCode;
    }


    public Patient getPatient() {
        return patient;
    }


    public void setPatient(Patient patient) {
        this.patient = patient;
    }


    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }


    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }


    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }


    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public String getNote() {
        return note;
    }


    public void setNote(String note) {
        this.note = note;
    }

}