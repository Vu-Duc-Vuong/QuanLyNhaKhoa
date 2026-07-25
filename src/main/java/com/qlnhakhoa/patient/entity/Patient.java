package com.qlnhakhoa.patient.entity;

import java.util.List;

import com.qlnhakhoa.appointment.entity.Appointment;

import jakarta.persistence.*;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "patients")
public class Patient {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column(unique = true, nullable = false)
    private String patientCode;



    @Column(nullable = false)
    private String fullName;



    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;



    private String gender;



    private String phone;



    private String address;



    private String cccd;



    @Column(length = 1000)
    private String medicalHistory;



    @Column(length = 1000)
    private String allergy;



    @Column(length = 1000)
    private String reason;



    // Danh sách lịch hẹn của bệnh nhân
    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments;



    public Patient() {
    }



    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }



    public String getPatientCode() {
        return patientCode;
    }


    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }



    public String getFullName() {
        return fullName;
    }


    public void setFullName(String fullName) {
        this.fullName = fullName;
    }



    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }


    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }



    public String getGender() {
        return gender;
    }


    public void setGender(String gender) {
        this.gender = gender;
    }



    public String getPhone() {
        return phone;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }



    public String getAddress() {
        return address;
    }


   public void setAddress(String address) {
    this.address = address;
}



    public String getCccd() {
        return cccd;
    }


    public void setCccd(String cccd) {
        this.cccd = cccd;
    }



    public String getMedicalHistory() {
        return medicalHistory;
    }


    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }



    public String getAllergy() {
        return allergy;
    }


    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }



    public String getReason() {
        return reason;
    }


    public void setReason(String reason) {
        this.reason = reason;
    }



    public List<Appointment> getAppointments() {
        return appointments;
    }


    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

}