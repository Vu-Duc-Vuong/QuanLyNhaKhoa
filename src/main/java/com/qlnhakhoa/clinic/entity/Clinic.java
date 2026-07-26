package com.qlnhakhoa.clinic.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "clinics")
public class Clinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // Mã định danh của nha khoa
    @Column(unique = true, nullable = false)
    private String clinicCode;


    // Tên nha khoa
    @Column(nullable = false)
    private String clinicName;


    // Địa chỉ nha khoa
    private String address;


    // Số điện thoại liên hệ
    private String phone;


    // Email nha khoa
    private String email;


    // ACTIVE / INACTIVE
    private String status;


    public Clinic() {
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getClinicCode() {
        return clinicCode;
    }


    public void setClinicCode(String clinicCode) {
        this.clinicCode = clinicCode;
    }


    public String getClinicName() {
        return clinicName;
    }


    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }


    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        this.address = address;
    }


    public String getPhone() {
        return phone;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }
}